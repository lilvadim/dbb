package ru.nsu.dbb.constructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class QueryConstructorTest {

    @Test
    void createAddColumnStatementTest() {
        QueryConstructor queryConstructor = new QueryConstructor();
        var actual = queryConstructor.createAddColumnStatement(
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
        QueryConstructor queryConstructor = new QueryConstructor();
        var actual = queryConstructor.createDropColumnStatement(
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
        QueryConstructor queryConstructor = new QueryConstructor();
        var actual = queryConstructor.createChangeColumnNameStatement(
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
        QueryConstructor queryConstructor = new QueryConstructor();
        var actual = queryConstructor.createIndexStatement(
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
        QueryConstructor queryConstructor = new QueryConstructor();
        var actual = queryConstructor.createDropIndexStatement("index");

        Assertions.assertEquals(
                "DROP INDEX index",
                actual
        );
    }
}