package ru.nsu.dbb.sql

class SqlQueryTypeDetector {
    fun getTypeOfQuery(query: String): QueryType {
        val normalizedQuery = query
            .trim()
            .replace(Regex.fromLiteral("\\s"), " ")
        return when {
            normalizedQuery.startsWith(EXPLAIN_PLAN_QUERY_TYPE, ignoreCase = true) -> QueryType.EXPLAIN_PLAN

            normalizedQuery.startsWith(CREATE_TABLE_QUERY_TYPE, ignoreCase = true)
                    || normalizedQuery.startsWith(DROP_TABLE_QUERY_TYPE, ignoreCase = true)
                    || normalizedQuery.startsWith(ALTER_TABLE_QUERY_TYPE, ignoreCase = true) -> QueryType.DDL

            normalizedQuery.startsWith(SELECT_QUERY_TYPE, ignoreCase = true) -> QueryType.SELECT

            normalizedQuery.startsWith(UPDATE_DATA_QUERY_TYPE, ignoreCase = true)
                    || normalizedQuery.startsWith(DELETE_DATA_QUERY_TYPE, ignoreCase = true)
                    || normalizedQuery.startsWith(INSERT_DATA_QUERY_TYPE, ignoreCase = true) -> QueryType.MODIFY

            else -> QueryType.UNKNOWN
        }
    }

    companion object {
        private const val SELECT_QUERY_TYPE = "SELECT"
        private const val UPDATE_DATA_QUERY_TYPE = "UPDATE"
        private const val DELETE_DATA_QUERY_TYPE = "DELETE"
        private const val INSERT_DATA_QUERY_TYPE = "INSERT"
        private const val CREATE_TABLE_QUERY_TYPE = "CREATE TABLE"
        private const val DROP_TABLE_QUERY_TYPE = "DROP TABLE"
        private const val ALTER_TABLE_QUERY_TYPE = "ALTER TABLE"
        private const val EXPLAIN_PLAN_QUERY_TYPE = "EXPLAIN"
    }
}
