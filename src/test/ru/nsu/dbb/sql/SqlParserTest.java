package ru.nsu.dbb.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.dbb.sql.QueryType;
import ru.nsu.dbb.sql.SqlParser;

public class SqlParserTest {
    @Test
    void getTypeOfQuerySelect() {
        SqlParser sqlParser = new SqlParser();
        var actualQueryType = sqlParser.getTypeOfQuery(
                """
                SELECT * FROM my_table
                """
        );
        Assertions.assertEquals(QueryType.SELECT, actualQueryType);
    }

    @Test
    void getTypeOfQueryUpdate() {
        SqlParser sqlParser = new SqlParser();
        var actualQueryType = sqlParser.getTypeOfQuery(
                """
                UPDATE my_table
                SET col1 = val1
                WHERE col2 = val2
                """
        );
        Assertions.assertEquals(QueryType.MODIFY, actualQueryType);
    }

    @Test
    void getTypeOfQueryDelete() {
        SqlParser sqlParser = new SqlParser();
        var actualQueryType = sqlParser.getTypeOfQuery(
                """
                DELETE FRON my_table
                WHERE col1 = val1
                """
        );
        Assertions.assertEquals(QueryType.MODIFY, actualQueryType);
    }

    @Test
    void getTypeOfQueryInsert() {
        SqlParser sqlParser = new SqlParser();
        var actualQueryType = sqlParser.getTypeOfQuery(
                """
                INSERT INTO my_table (col1, col2)
                VALUES (val1, val2)
                """
        );
        Assertions.assertEquals(QueryType.MODIFY, actualQueryType);
    }

    @Test
    void getTypeOfQueryCreateTable() {
        SqlParser sqlParser = new SqlParser();
        var actualQueryType = sqlParser.getTypeOfQuery(
                """
                CREATE TABLE my_table (
                    col1 String,
                    col2 String
                )
                """
        );
        Assertions.assertEquals(QueryType.DDL, actualQueryType);
    }

    @Test
    void getTypeOfQueryDropTable() {
        SqlParser sqlParser = new SqlParser();
        var actualQueryType = sqlParser.getTypeOfQuery(
                """
                DROP TABLE my_table
                """
        );
        Assertions.assertEquals(QueryType.DDL, actualQueryType);
    }

    @Test
    void getTypeOfQueryAlterTable() {
        SqlParser sqlParser = new SqlParser();
        var actualQueryType = sqlParser.getTypeOfQuery(
                """
                ALTER TABLE my_table
                ADD COLUMN new_col String
                """
        );
        Assertions.assertEquals(QueryType.DDL, actualQueryType);
    }

    @Test
    void getTypeOfQueryExplainPlan() {
        SqlParser sqlParser = new SqlParser();
        var actualQueryType = sqlParser.getTypeOfQuery(
                """
                EXPLAIN PLAN SELECT * FROM my_table
                """
        );
        Assertions.assertEquals(QueryType.EXPLAIN_PLAN, actualQueryType);
    }

    @Test
    void getTypeOfQueryUnknown() {
        SqlParser sqlParser = new SqlParser();
        var actualQueryType = sqlParser.getTypeOfQuery(
                """
                something unknown
                """
        );
        Assertions.assertEquals(QueryType.UNKNOWN, actualQueryType);
    }
}
