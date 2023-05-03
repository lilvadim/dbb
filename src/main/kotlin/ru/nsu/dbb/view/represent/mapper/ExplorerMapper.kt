package ru.nsu.dbb.view.represent.mapper

import javafx.scene.control.TreeItem
import ru.nsu.dbb.entity.*
import ru.nsu.dbb.view.represent.ExplorerItem

private const val DATABASE_STORAGE_LABEL = "Database"

private const val INDEXES_LABEL = "Indexes"

private const val PRIMARY_KEYS_LABEL = "Primary Keys"

private const val FOREIGN_KEYS_LABEL = "Foreign Keys"

private const val PRIMARY_KEY_LABEL = "Primary Key"

private const val COLUMNS_LABEL = "Columns"

private const val CATALOG_LABEL = "Catalog"

private const val SCHEMA_LABEL = "Schema"

class ExplorerMapper {
    fun mapToTreeView(databaseStorage: DatabaseStorage): TreeItem<ExplorerItem> {
        return mapToTreeItem(mapToExplorerItem(databaseStorage))
    }

    private fun mapToExplorerItem(databaseStorage: DatabaseStorage): ExplorerItem {
        return ExplorerItem(
            DATABASE_STORAGE_LABEL,
            databaseStorage,
            databaseStorage.storage.values.map { mapToExplorerItem(it) })
    }

    private fun mapToExplorerItem(database: Database): ExplorerItem {
        return ExplorerItem(
            database.name,
            database,
            database.catalogs.mapIndexed { index, catalog -> mapToExplorerItem(catalog, index) })
    }

    private fun mapToExplorerItem(catalog: Catalog, idx: Int): ExplorerItem {
        return ExplorerItem(
            catalog.name ?: indexedLabel(CATALOG_LABEL, idx),
            catalog,
            catalog.schemas.mapIndexed { index, schema -> mapToExplorerItem(schema, index) }
        )
    }

    private fun mapToExplorerItem(schema: Schema, idx: Int): ExplorerItem {
        return ExplorerItem(
            schema.name ?: indexedLabel(SCHEMA_LABEL, idx),
            schema,
            schema.tables.map { mapToExplorerItem(it) }
        )
    }

    private fun mapToExplorerItem(table: Table): ExplorerItem {
        return ExplorerItem(
            table.name, table,
            listOf(
                columnsToExplorerItem(table.columns),
                primaryKeysToExplorerItem(table.primaryKeys),
                foreignKeysToExplorerItem(table.foreignKeys),
                indexesToExplorerItem(table.indexes)
            )
        )
    }

    private fun indexesToExplorerItem(indexes: List<Index>): ExplorerItem {
        return ExplorerItem(INDEXES_LABEL, indexes, indexes.map { mapToExplorerItem(it) })
    }

    private fun mapToExplorerItem(index: Index): ExplorerItem {
        return ExplorerItem(index.name, index)
    }

    private fun primaryKeysToExplorerItem(primaryKeys: List<PrimaryKey>): ExplorerItem {
        return ExplorerItem(
            PRIMARY_KEYS_LABEL,
            primaryKeys,
            primaryKeys.mapIndexed { index, primaryKey -> mapToExplorerItem(primaryKey, index) })
    }

    private fun mapToExplorerItem(primaryKey: PrimaryKey, idx: Int): ExplorerItem {
        return ExplorerItem(primaryKey.primaryKeyName ?: indexedLabel(PRIMARY_KEY_LABEL, idx), primaryKey)
    }

    private fun indexedLabel(label: String, idx: Int) =
        "$label #$idx"

    private fun foreignKeyLabel(foreignKey: ForeignKey): String =
        "${foreignKey.foreignKeyTableName} -> ${foreignKey.primaryKeyTableName}"

    private fun foreignKeysToExplorerItem(foreignKeys: List<ForeignKey>): ExplorerItem {
        return ExplorerItem(FOREIGN_KEYS_LABEL, foreignKeys, foreignKeys.map { mapToExplorerItem(it) })
    }

    private fun mapToExplorerItem(foreignKey: ForeignKey): ExplorerItem {
        return ExplorerItem(foreignKeyLabel(foreignKey), foreignKey)
    }

    private fun columnsToExplorerItem(columns: List<Column>): ExplorerItem {
        return ExplorerItem(COLUMNS_LABEL, columns, columns.map { mapToExplorerItem(it) })
    }

    private fun mapToExplorerItem(column: Column): ExplorerItem {
        return ExplorerItem(column.name, column)
    }

    private fun mapToTreeItem(item: ExplorerItem): TreeItem<ExplorerItem> {
        return TreeItem(item).apply {
            children += item.children.map { mapToTreeItem(it) }
        }
    }
}