package ru.nsu.dbb.constructor;

public class QueryConstructor {
    private static final String addColumnStatementTemplate = "ALTER TABLE %s ADD COLUMN %s %s %s";

    private static final String dropColumnStatementTemplate = "ALTER TABLE %s DROP COLUMN %s";

    private static final String renameColumnStatementTemplate = "ALTER TABLE %s RENAME %s TO %s";

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


}
