package ru.nsu.dbb.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SqlParserTest {
    @Test
    void getTypeOfQuerySelect() {
        SqlQueryTypeDetector sqlQueryTypeDetector = new SqlQueryTypeDetector();
        var actualQueryType = sqlQueryTypeDetector.getTypeOfQuery(
                """
                        SELECT * FROM my_table
                        """
        );
        Assertions.assertEquals(QueryType.SELECT, actualQueryType);
    }

    @Test
    void getTypeOfQueryUpdate() {
        SqlQueryTypeDetector sqlQueryTypeDetector = new SqlQueryTypeDetector();
        var actualQueryType = sqlQueryTypeDetector.getTypeOfQuery(
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
        SqlQueryTypeDetector sqlQueryTypeDetector = new SqlQueryTypeDetector();
        var actualQueryType = sqlQueryTypeDetector.getTypeOfQuery(
                """
                        DELETE FRON my_table
                        WHERE col1 = val1
                        """
        );
        Assertions.assertEquals(QueryType.MODIFY, actualQueryType);
    }

    @Test
    void getTypeOfQueryInsert() {
        SqlQueryTypeDetector sqlQueryTypeDetector = new SqlQueryTypeDetector();
        var actualQueryType = sqlQueryTypeDetector.getTypeOfQuery(
                """
                        INSERT INTO my_table (col1, col2)
                        VALUES (val1, val2)
                        """
        );
        Assertions.assertEquals(QueryType.MODIFY, actualQueryType);
    }

    @Test
    void getTypeOfQueryCreateTable() {
        SqlQueryTypeDetector sqlQueryTypeDetector = new SqlQueryTypeDetector();
        var actualQueryType = sqlQueryTypeDetector.getTypeOfQuery(
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
        SqlQueryTypeDetector sqlQueryTypeDetector = new SqlQueryTypeDetector();
        var actualQueryType = sqlQueryTypeDetector.getTypeOfQuery(
                """
                        DROP TABLE my_table
                        """
        );
        Assertions.assertEquals(QueryType.DDL, actualQueryType);
    }

    @Test
    void getTypeOfQueryAlterTable() {
        SqlQueryTypeDetector sqlQueryTypeDetector = new SqlQueryTypeDetector();
        var actualQueryType = sqlQueryTypeDetector.getTypeOfQuery(
                """
                        ALTER TABLE my_table
                        ADD COLUMN new_col String
                        """
        );
        Assertions.assertEquals(QueryType.DDL, actualQueryType);
    }

    @Test
    void getTypeOfQueryExplainPlan() {
        SqlQueryTypeDetector sqlQueryTypeDetector = new SqlQueryTypeDetector();
        var actualQueryType = sqlQueryTypeDetector.getTypeOfQuery(
                """
                        EXPLAIN PLAN SELECT * FROM my_table
                        """
        );
        Assertions.assertEquals(QueryType.EXPLAIN_PLAN, actualQueryType);
    }

    @Test
    void getTypeOfQueryUnknown() {
        SqlQueryTypeDetector sqlQueryTypeDetector = new SqlQueryTypeDetector();
        var actualQueryType = sqlQueryTypeDetector.getTypeOfQuery(
                """
                        something unknown
                        """
        );
        Assertions.assertEquals(QueryType.UNKNOWN, actualQueryType);
    }
}
