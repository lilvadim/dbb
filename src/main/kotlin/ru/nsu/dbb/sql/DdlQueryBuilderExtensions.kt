package ru.nsu.dbb.sql

fun DdlQueryBuilder.createStatement(
    sqlOperationType: SqlOperationType,
    parameters: Map<SqlOperationParameter, Any>
): String {
    return when (sqlOperationType) {
        SqlOperationType.DDL_CREATE_COLUMN -> createAddColumnStatement(
            parameters[SqlOperationParameter.TABLE_NAME] as String,
            parameters[SqlOperationParameter.COLUMN_NAME] as String,
            parameters[SqlOperationParameter.TYPE] as String,
            parameters[SqlOperationParameter.NOT_NULL] as Boolean
        )

        else -> throw IllegalStateException()
    }
}