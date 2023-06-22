package ru.nsu.dbb.sql.ddl

enum class DdlOperationType(
    val description: String,
    val parameters: List<DdlOperationParameter> = listOf()
) {
    CREATE_COLUMN(
        "Create Column",
        listOf(
            DdlOperationParameter.TABLE_NAME,
            DdlOperationParameter.COLUMN_NAME,
            DdlOperationParameter.TYPE,
            DdlOperationParameter.NOT_NULL
        )
    ),
    DROP_COLUMN(
        "Drop Column",
        listOf(
            DdlOperationParameter.TABLE_NAME,
            DdlOperationParameter.COLUMN_NAME
        )
    ),
    CHANGE_COLUMN_NAME(
        "Change Column Name",
        listOf(
            DdlOperationParameter.TABLE_NAME,
            DdlOperationParameter.COLUMN_NAME,
            DdlOperationParameter.NEW_COLUMN_NAME
        )
    ),
    DROP_INDEX(
        "Drop Index",
        listOf(
            DdlOperationParameter.INDEX_NAME
        )
    ),
    CREATE_TABLE(
        "Create Table",
        listOf(
            DdlOperationParameter.TABLE_NAME
        )
    ),
    CREATE_INDEX(
        "Create Index",
        listOf(
            DdlOperationParameter.TABLE_NAME,
            DdlOperationParameter.INDEX_NAME,
            DdlOperationParameter.COLUMNS
        )
    );

    override fun toString(): String = description
}