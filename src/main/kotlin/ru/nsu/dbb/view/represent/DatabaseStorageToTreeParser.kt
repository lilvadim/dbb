package ru.nsu.dbb.view.represent

import javafx.scene.control.TreeItem
import ru.nsu.dbb.entity.DatabaseStorage

// TODO разбить на отдельные функции и сложить в маппинг TreeItem -> Object
class DatabaseStorageToTreeParser {
    fun parse(databaseStorage: DatabaseStorage): TreeItem<String> {
        val storage = databaseStorage.storage
        return TreeItem("Databases").apply {
            children.addAll(
                storage.values.map { db ->
                    TreeItem(db.name) % {
                        children.addAll(
                            db.catalogs.mapIndexed { idx, cat ->
                                TreeItem(cat.name ?: "catalog #$idx") % {
                                    children.addAll(
                                        cat.schemas.mapIndexed { idx, sch ->
                                            TreeItem(sch.name ?: "schema #${idx + 1}") % {
                                                children.addAll(
                                                    TreeItem("tables") % {
                                                        children.addAll(
                                                            sch.tables.map { tab ->
                                                                TreeItem(tab.name) % {
                                                                    children.addAll(
                                                                        TreeItem("columns") % {
                                                                            children.addAll(
                                                                                tab.columns.map { col ->
                                                                                    TreeItem(col.name)
                                                                                }
                                                                            )
                                                                        },
                                                                        TreeItem("primary keys") % {
                                                                            children.addAll(
                                                                                tab.primaryKeys.mapIndexed { idx, pk ->
                                                                                    TreeItem(
                                                                                        pk.primaryKeyName
                                                                                            ?: "primary key #${idx + 1}"
                                                                                    )
                                                                                }
                                                                            )
                                                                        },
                                                                        TreeItem("foreign keys") % {
                                                                            children.addAll(
                                                                                tab.foreignKeys.mapIndexed { idx, fk ->
                                                                                    TreeItem("foreign key #${idx + 1} : ${fk.foreignKeyTableName} -> ${fk.primaryKeyTableName}")
                                                                                }
                                                                            )
                                                                        },
                                                                        TreeItem("indexes") % {
                                                                            children.addAll(
                                                                                tab.indexes.map { ind ->
                                                                                    TreeItem(ind.name)
                                                                                }
                                                                            )
                                                                        }
                                                                    )
                                                                }
                                                            }
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}

private operator fun <T> TreeItem<T>.rem(config: TreeItem<T>.() -> Unit): TreeItem<T> = apply(config)