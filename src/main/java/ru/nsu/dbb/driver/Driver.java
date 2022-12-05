package ru.nsu.dbb.driver;

import ru.nsu.dbb.entity.*;

import java.sql.*;
import java.util.ArrayList;


public class Driver {
    private Connection connection;

    public void openConnection(String url, String user, String password) throws SQLException {
        if (connection != null)
            connection.close();
        connection = DriverManager.getConnection(url, user, password);
    }

    private static final String TABLE_CAT = "TABLE_CAT";
    private static final String TABLE_CATALOG = "TABLE_CATALOG";
    private static final String TABLE_SCHEM = "TABLE_SCHEM";
    private static final String TABLE = "TABLE";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String COLUMN_SIZE = "COLUMN_SIZE";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String IS_NULLABLE = "IS_NULLABLE";
    private static final String PK_NAME = "PK_NAME";
    private static final String PKTABLE_NAME = "PKTABLE_NAME";
    private static final String FKTABLE_NAME = "FKTABLE_NAME";
    private static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
    private static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";

    private static final String INDEX_NAME = "INDEX_NAME";

    private static final String NON_UNIQUE = "NON_UNIQUE";
    public Database getDatabaseMeta() throws SQLException {
        Database database = new Database();
        var metaData = connection.getMetaData();

        ArrayList<CataLog> catalogs = new ArrayList<>();
        try (var resultSetCatalogs = metaData.getCatalogs()){
            if (!resultSetCatalogs.isBeforeFirst()) {
                CataLog catalog = new CataLog();
                catalog.setSchemas(this.getSchemas(null, metaData));
                catalogs.add(catalog);
            } else {
                while (resultSetCatalogs.next()) {
                    CataLog cataLog = new CataLog();
                    var currentCatalogName = resultSetCatalogs.getString(TABLE_CAT);
                    cataLog.setSchemas(this.getSchemas(currentCatalogName, metaData));
                    cataLog.setName(currentCatalogName);
                    catalogs.add(cataLog);
                }
            }
        }
        database.setCatalogs(catalogs);
        return database;
    }

    private ArrayList<Schema> getSchemas(String catalogName, DatabaseMetaData metaData) throws SQLException {
        ArrayList<Schema> schemas = new ArrayList<>();
        try (var resultSetSchemas = metaData.getSchemas()) {
            if (!resultSetSchemas.isBeforeFirst()) {
                Schema schema = new Schema();
                schema.setTables(this.getTables(catalogName, null, metaData));
                schemas.add(schema);
            } else {
                while (resultSetSchemas.next()) {
                    Schema schema = new Schema();
                    var currentCatalogName = resultSetSchemas.getString(TABLE_CATALOG);
                    if (currentCatalogName == null || catalogName == null) {
                        var schemaName = resultSetSchemas.getString(TABLE_SCHEM);
                        schema.setTables(this.getTables(catalogName, schemaName, metaData));
                        schema.setName(schemaName);
                    } else {
                        if (currentCatalogName.equals(catalogName)) {
                            var schemaName = resultSetSchemas.getString(TABLE_SCHEM);
                            schema.setTables(this.getTables(catalogName, schemaName, metaData));
                            schema.setName(schemaName);
                        }
                    }
                    schemas.add(schema);
                }
            }
        }
        return schemas;
    }
    private ArrayList<Table> getTables(String catalogName, String schemaName, DatabaseMetaData metaData) throws SQLException {
        ArrayList<Table> tables = new ArrayList<>();
        var resultSetTables = metaData.getTables(catalogName, schemaName, null, new String[]{TABLE});
        while (resultSetTables.next()) {
            var tableName = resultSetTables.getString(TABLE_NAME);
            var table = new Table(tableName);
            table.setColumns(this.getColumns(catalogName, schemaName, tableName, metaData));
            table.setPrimaryKeys(this.getPrimaryKeys(catalogName, schemaName, tableName, metaData));
            table.setForeignKeys(this.getForeignKeys(catalogName, schemaName, tableName, metaData));
            table.setIndexes(this.getIndexes(catalogName, schemaName, tableName, metaData));
            tables.add(table);
        }
        return tables;
    }

    private ArrayList<Column> getColumns(String catalogName, String schemaName, String tableName, DatabaseMetaData metaData) throws SQLException {
        ArrayList<Column> columns = new ArrayList<>();
        try (var resultSetColumns = metaData.getColumns(catalogName, schemaName, tableName, null)) {
            while (resultSetColumns.next()) {
                var columnName = resultSetColumns.getString(COLUMN_NAME);
                var columnSize = resultSetColumns.getString(COLUMN_SIZE);
                var datatype = resultSetColumns.getString(DATA_TYPE);
                var isNullable = resultSetColumns.getString(IS_NULLABLE);
                columns.add(new Column(columnName, datatype, columnSize, isNullable));
            }
        }
        return columns;
    }

    private ArrayList<PrimaryKey> getPrimaryKeys(String catalogName, String schemaName, String tableName, DatabaseMetaData metaData) throws SQLException {
        ArrayList<PrimaryKey> primaryKeys = new ArrayList<>();
        try (var ResultSetPrimaryKeys = metaData.getPrimaryKeys(catalogName, schemaName, tableName)) {
            var primaryKey = new PrimaryKey();
            while (ResultSetPrimaryKeys.next()) {
                var primaryKeyName = ResultSetPrimaryKeys.getString(PK_NAME);
                primaryKey.setPrimaryKeyName(primaryKeyName);
                var primaryKeyColumnName = ResultSetPrimaryKeys.getString(COLUMN_NAME);
                primaryKey.addColumnToPrimaryKey(primaryKeyColumnName);
                primaryKeys.add(primaryKey);
            }
        }
        return primaryKeys;
    }

    private ArrayList<ForeignKey> getForeignKeys(String catalogName, String schemaName, String tableName, DatabaseMetaData metaData) throws SQLException {
        ArrayList<ForeignKey> foreignKeys = new ArrayList<>();
        try (var resultSetForeignKeys = metaData.getImportedKeys(catalogName, schemaName, tableName)) {
            String tempPkTableName = "";
            ForeignKey foreignKey = new ForeignKey();
            while (resultSetForeignKeys.next()) {
                var pkTableName = resultSetForeignKeys.getString(PKTABLE_NAME);
                var fkTableName = resultSetForeignKeys.getString(FKTABLE_NAME);
                var pkColumnName = resultSetForeignKeys.getString(PKCOLUMN_NAME);
                var fkColumnName = resultSetForeignKeys.getString(FKCOLUMN_NAME);
                if (!tempPkTableName.equals(pkTableName)) {
                    foreignKey = new ForeignKey();
                }
                foreignKey.addMapping(fkColumnName, pkColumnName);
                foreignKey.setPKTableName(pkTableName);
                foreignKey.setFKTableName(fkTableName);
                foreignKeys.add(foreignKey);
            }
        }
        return foreignKeys;
    }

    private ArrayList<Index> getIndexes(String catalogName, String schemaName, String tableName, DatabaseMetaData metaData) throws SQLException {
        ArrayList<Index> indexes = new ArrayList<>();
        try (var resultSetIndexes = metaData.getIndexInfo(catalogName, schemaName, tableName, true, false)){
            while (resultSetIndexes.next()) {
                Index index = new Index(
                        resultSetIndexes.getString(INDEX_NAME),
                        resultSetIndexes.getBoolean(NON_UNIQUE),
                        resultSetIndexes.getString(COLUMN_NAME)
                );
                indexes.add(index);
            }
        }
        return indexes;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }
}

