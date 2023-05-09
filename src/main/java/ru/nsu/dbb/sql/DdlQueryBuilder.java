package ru.nsu.dbb.sql;

import java.util.ArrayList;
import java.util.List;

public class DdlQueryBuilder {
    private static final String addColumnStatementTemplate = "ALTER TABLE %s ADD COLUMN %s %s %s";

    private static final String dropColumnStatementTemplate = "ALTER TABLE %s DROP COLUMN %s";

    private static final String renameColumnStatementTemplate = "ALTER TABLE %s RENAME %s TO %s";

    private static final String createIndexStatementTemplate = "CREATE INDEX %s ON %s (%s)";

    private static final String dropIndexStatementTemplate = "DROP INDEX %s";

    private List<String> tableStructure;


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
        StringBuilder columns = new StringBuilder();
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

    public String createTableStatememt(String tableName) {
        tableStructure = new ArrayList<>();
        tableStructure.add("CREATE TABLE (");
        tableStructure.add(");");
        return getResultString();
    }

    public String addColumnToCreateTable(String columnName, String columnType) {
        tableStructure.add(
                tableStructure.size() - 1,
                String.format("%s %s",
                        columnName,
                        columnType
                )
        );
        return getResultString();
    }

    public String addPrimaryKeyToCreateTable(String constraintName, List<String> columnsInPrimaryKey) {
        StringBuilder result = new StringBuilder();
        result.append(
                String.format("CONSTRAINT %s PRIMARY KEY (", constraintName));
        for (int i = 0; i < columnsInPrimaryKey.size(); i++) {
            if (i != columnsInPrimaryKey.size() - 1) {
                result.append(columnsInPrimaryKey.get(i)).append(",");
            } else {
                result.append(columnsInPrimaryKey.get(i));
            }
        }
        result.append(")");
        tableStructure.add(
                tableStructure.size() - 1,
                result.toString()
      );
        return getResultString();
    }

    public String addForeignKeyToCreateTable(String constraintName,
                                             String targetTable,
                                             List<String> columnsInForeignKey,
                                             List<String> columnsInOtherTable) {
        StringBuilder result = new StringBuilder();
        result.append(
                String.format("CONSTRAINT %s FOREIGN KEY (", constraintName)
        );
        for (int i = 0; i < columnsInForeignKey.size(); i++) {
            if (i != columnsInForeignKey.size() - 1) {
                result.append(columnsInForeignKey.get(i)).append(",");
            } else {
                result.append(columnsInForeignKey.get(i));
            }
        }
        result.append(
                String.format(")\nREFERENCES %s (", targetTable)
        );
        for (int i = 0; i < columnsInOtherTable.size(); i++) {
            if (i != columnsInOtherTable.size() - 1) {
                result.append(columnsInOtherTable.get(i)).append(",");
            } else {
                result.append(columnsInOtherTable.get(i));
            }
        }
        result.append(")");
        tableStructure.add(
                tableStructure.size() - 1,
                result.toString()
        );
        return getResultString();
    }
    private String getResultString() {
        StringBuilder result = new StringBuilder();
        for (String part:tableStructure) {
            result.append(part).append("\n");
        }
        return result.toString();
    }
}
