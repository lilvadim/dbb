package ru.nsu.dbb.constructor;

import java.util.ArrayList;

public class QueryConstructor {
    private static final String addColumnStatementTemplate = "ALTER TABLE %s ADD COLUMN %s %s %s";

    private static final String dropColumnStatementTemplate = "ALTER TABLE %s DROP COLUMN %s";

    private static final String renameColumnStatementTemplate = "ALTER TABLE %s RENAME %s TO %s";

    private static final String createIndexStatementTemplate  = "CREATE INDEX %s ON %s (%s)";

    private static final String dropIndexStatementTemplate = "DROP INDEX %s";

    // todo сделать для других бд, так как sqlite не поддерживает изменение типа колонки
    private static final String changeColumnDataTypeTemplate = "";

    public String createAddColumnStatement(String tableName, String columnName, String type, boolean notNull) {
        return String.format(
                addColumnStatementTemplate,
                tableName,
                columnName,
                type,
                notNull ? "not null" : "");
    }

    public String createDropColumnStatement(String tableName, String columnName) {
        return String.format(dropColumnStatementTemplate, tableName, columnName);
    }

    public String createChangeColumnNameStatement(String tableName, String oldColumnName, String newColumnName) {
        return String.format(
                renameColumnStatementTemplate,
                tableName,
                oldColumnName,
                newColumnName
        );
    }

    public String createIndexStatement(String tableName, String indexName, ArrayList<String> columnsInIndex) {
        StringBuilder columns = new StringBuilder("");
        var size = columnsInIndex.size();
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                columns.append(columnsInIndex.get(i)).append(",");
            } else {
                columns.append(columnsInIndex.get(i));
            }
        }
        return String.format(
                createIndexStatementTemplate,
                indexName,
                tableName,
                columns
        );
    }

    public String createDropIndexStatement(String indexName) {
        return String.format(
                dropIndexStatementTemplate,
                indexName
        );
    }
}
