package ru.nsu.dbb.sql

enum class SqlOperationType(
    val description: String
) {
    DDL_CREATE_COLUMN("Create Column"),
    DDL_DROP_COLUMN("Drop Column"),
    DDL_CHANGE_COLUMN_NAME("Change Column Name"),
    DDL_DROP_INDEX("Drop Index"),
    DDL_CREATE_TABLE("Drop Index"),
    DDL_CREATE_INDEX("Create Index");

    override fun toString(): String = description
}