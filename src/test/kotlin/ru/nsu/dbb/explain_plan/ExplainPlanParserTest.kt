package ru.nsu.dbb.explain_plan

import org.junit.jupiter.api.Test
import ru.nsu.dbb.config.driver.Driver

class ExplainPlanParserTest {

    @Test
    fun explainPlan() {
        val mysqlQuery1 = "explain select MAX(PersonID) from Persons\n" +
                "where PersonID >= 2 and City != 'qwe'\n" +
                "group by FirstName\n" +
                "order by FirstName desc"

        val sqliteQuery2 = "EXPLAIN QUERY PLAN select * from genres union select * from artists\n" +
                "order by GenreId"

        val postgresQuery1 = "explain select max(test_table.age) from test_table\n" +
                "inner join i_table it on test_table.name = it.name\n" +
                "inner join t_table tt on test_table.age = tt.age\n" +
                "where test_table.age < 3\n" +
                "group by test_table.age\n" +
                "order by test_table.age"

        val postgresQuery2 = "explain select * from test_table"

        val postgresQuery3 = "explain insert into test_table VALUES ('exp1', 1), ('exp2', 2)"

        val postgresQuery4 = "explain delete from test_table where name='exp1'"

        val sqliteQuery1 = "explain select title1 from albums\n" +
                "where AlbumId <= 20 and ArtistId >= 2\n" +
                "order by title1"

        val driver = Driver()
        //driver.openConnection("jdbc:mysql://localhost:3306/myDB", "mysql", "123")
        driver.openConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123")
        //driver.openConnection("jdbc:sqlite:src/test/resources/chinook.db", "", "")
        val statement = driver.createStatement()
        val resultSet = statement.executeQuery(postgresQuery1)

//        while (resultSet.next()) {
//            val str = resultSet.getString(4)
//            println(str)
//            println(explainLiteRegexParser(str))
//            println()
//        }
        //val root = explainResultSetToTree(resultSet, "Select", SQLDialect.SQLITE)
        val root = explainResultSetToTree(rs = resultSet, queryName = "Select", dialect = SQLDialect.POSTGRE)
        println()
    }
}