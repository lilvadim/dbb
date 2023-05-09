package ru.nsu.dbb.sql

import kotlin.reflect.KClass

enum class SqlOperationParameter(
    val id: String,
    val fullName: String,
    val type: KClass<*>
) {
    TABLE_NAME("tableName", "Table Name", String::class),
    COLUMN_NAME("columnName", "Column Name", String::class),
    TYPE("type", "Type", String::class),
    NOT_NULL("notNull", "Not Null", Boolean::class),
}