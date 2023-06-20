package ru.nsu.dbb.sql

enum class SqlOperationType(
    val description: String,
    val parameters: List<SqlOperationParameter> = listOf()
) {
    DDL_CREATE_COLUMN(
        "Create Column",
        listOf(
            SqlOperationParameter.COLUMN_NAME,
            SqlOperationParameter.TYPE,
            SqlOperationParameter.NOT_NULL
        )
    ),
    DDL_DROP_COLUMN(
        "Drop Column",
        listOf(
            SqlOperationParameter.TABLE_NAME,
            SqlOperationParameter.COLUMN_NAME
        )
    ),
    DDL_CHANGE_COLUMN_NAME(
        "Change Column Name",
        listOf(
            SqlOperationParameter.TABLE_NAME,
            SqlOperationParameter.NEW_COLUMN_NAME
        )
    ),
    DDL_DROP_INDEX(
        "Drop Index",
        listOf(
            SqlOperationParameter.INDEX_NAME
        )
    ),
    DDL_CREATE_TABLE(
        "Drop Index",
        listOf(
            SqlOperationParameter.TABLE_NAME
        )
    ),
    DDL_CREATE_INDEX(
        "Create Index",
        listOf(
            SqlOperationParameter.TABLE_NAME,
            SqlOperationParameter.INDEX_NAME,
            SqlOperationParameter.COLUMNS
        )
    );

    override fun toString(): String = description
}