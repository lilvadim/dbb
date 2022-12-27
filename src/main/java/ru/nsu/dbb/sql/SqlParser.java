package ru.nsu.dbb.sql;

public class SqlParser {
    private static final String SELECT_QUERY_TYPE = "SELECT";
    private static final String UPDATE_DATA_QUERY_TYPE = "UPDATE";
    private static final String DELETE_DATA_QUERY_TYPE = "DELETE";
    private static final String INSERT_DATA_QUERY_TYPE = "INSERT";

    private static final String CREATE_TABLE_QUERY_TYPE = "CREATE TABLE";
    private static final String DROP_TABLE_QUERY_TYPE = "DROP TABLE";
    private static final String ALTER_TABLE_QUERY_TYPE = "ALTER TABLE";

    private static final String EXPLAIN_PLAN_QUERY_TYPE = "EXPLAIN QUERY PLAN";
    public QueryType getTypeOfQuery(String query) {
        if (query.contains(EXPLAIN_PLAN_QUERY_TYPE))
            return QueryType.EXPLAIN_PLAN;
        if (query.contains(SELECT_QUERY_TYPE))
            return QueryType.SELECT;
        if (query.contains(UPDATE_DATA_QUERY_TYPE)
                || query.contains(DELETE_DATA_QUERY_TYPE)
                || query.contains(INSERT_DATA_QUERY_TYPE)) {
            return QueryType.MODIFY;
        }
        if (query.contains(CREATE_TABLE_QUERY_TYPE)
                || query.contains(DROP_TABLE_QUERY_TYPE)
                || query.contains(ALTER_TABLE_QUERY_TYPE)) {
            return QueryType.DDL;
        }
        return QueryType.UNKNOWN;
    }
}
