package ru.nsu.dbb.sql.ddl

import ru.nsu.dbb.sql.DdlQueryBuilder

fun DdlQueryBuilder.createStatement(
    ddlOperationType: DdlOperationType,
    parameters: Map<DdlOperationParameter, Any>
): String {
    return when (ddlOperationType) {
        DdlOperationType.CREATE_COLUMN -> createAddColumnStatement(
            parameters[DdlOperationParameter.TABLE_NAME] as String,
            parameters[DdlOperationParameter.COLUMN_NAME] as String,
            parameters[DdlOperationParameter.TYPE] as String,
            parameters[DdlOperationParameter.NOT_NULL] as Boolean
        )

        DdlOperationType.DROP_COLUMN -> createDropColumnStatement(
            parameters[DdlOperationParameter.TABLE_NAME] as String,
            parameters[DdlOperationParameter.COLUMN_NAME] as String,
        )

        DdlOperationType.CREATE_TABLE -> createTableStatememt(
            parameters[DdlOperationParameter.TABLE_NAME] as String
        )

        DdlOperationType.CREATE_INDEX -> createIndexStatement(
            parameters[DdlOperationParameter.TABLE_NAME] as String,
            parameters[DdlOperationParameter.INDEX_NAME] as String,
            parameters[DdlOperationParameter.COLUMNS] as List<String>
        )

        DdlOperationType.DROP_INDEX -> createDropIndexStatement(
            parameters[DdlOperationParameter.INDEX_NAME] as String
        )

        DdlOperationType.CHANGE_COLUMN_NAME -> createChangeColumnNameStatement(
            parameters[DdlOperationParameter.TABLE_NAME] as String,
            parameters[DdlOperationParameter.COLUMN_NAME] as String,
            parameters[DdlOperationParameter.NEW_COLUMN_NAME] as String
        )

        else -> throw IllegalStateException()
    }
}