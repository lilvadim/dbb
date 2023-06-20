package ru.nsu.dbb.sql

import kotlin.reflect.KClass

enum class SqlOperationParameter(
    val fullName: String,
    val type: KClass<*>
) {
    TABLE_NAME("Table Name", String::class),
    COLUMN_NAME("Column Name", String::class),
    TYPE("Type", String::class),
    NOT_NULL("Not Null", Boolean::class),
    NEW_COLUMN_NAME("New Column Name", String::class),
    INDEX_NAME("Index Name", String::class),
    COLUMNS("Columns", List::class),
}