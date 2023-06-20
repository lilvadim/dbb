package ru.nsu.dbb.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DdlQueryBuilderTest {

    @Test
    void createAddColumnStatementTest() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        var actual = ddlQueryBuilder.createAddColumnStatement(
                "my_table",
                "new_col",
                "String",
                true
        );

        Assertions.assertEquals(
                "ALTER TABLE my_table ADD COLUMN new_col String not null",
                actual
        );
    }

    @Test
    void createDropColumnStatementTest() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        var actual = ddlQueryBuilder.createDropColumnStatement(
                "my_table",
                "new_col"
        );

        Assertions.assertEquals(
                "ALTER TABLE my_table DROP COLUMN new_col",
                actual
        );
    }

    @Test
    void createChangeColumnNameStatementTest() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        var actual = ddlQueryBuilder.createChangeColumnNameStatement(
                "my_table",
                "old_col",
                "new_col"
        );

        Assertions.assertEquals(
                "ALTER TABLE my_table RENAME old_col TO new_col",
                actual
        );
    }

    @Test
    void createIndexStatementTest() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        var actual = ddlQueryBuilder.createIndexStatement(
                "my_table",
                "new_index",
                new ArrayList<>(List.of("col1", "col2"))
        );

        Assertions.assertEquals(
                "CREATE INDEX new_index ON my_table (col1,col2)",
                actual
        );
    }

    @Test
    void createDropIndexStatementTest() {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        var actual = ddlQueryBuilder.createDropIndexStatement("index");

        Assertions.assertEquals(
                "DROP INDEX index",
                actual
        );
    }
}