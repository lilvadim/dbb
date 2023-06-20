package ru.nsu.dbb.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.nsu.dbb.driver.Driver;
import ru.nsu.dbb.entity.ConsoleLog;
import ru.nsu.dbb.entity.DatabaseStorage;
import ru.nsu.dbb.exceptions.QueryNotModifiableException;
import ru.nsu.dbb.exceptions.UnknownQueryTypeException;
import ru.nsu.dbb.explain_plan.SQLDialect;
import ru.nsu.dbb.response.ExplainPlanResultPipe;
import ru.nsu.dbb.response.SelectResultPipe;
import ru.nsu.dbb.sql.SqlParser;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.nsu.dbb.explain_plan.ExplainPlanParserKt.explainResultSetToTree;

@RequiredArgsConstructor(onConstructor_ = {@Inject})
@Getter
public class ConsoleController {
    private final Driver driver;
    private final SqlParser sqlParser;

    private final DatabaseStorage databaseStorage;

    private final ConsoleLog consoleLog;
    private final ExplainPlanResultPipe explainPlanResultPipe;
    private final SelectResultPipe selectResultPipe;

    private Statement statement;

    public void runQuery(String query) throws UnknownQueryTypeException, QueryNotModifiableException, SQLException {
        if (statement != null) {
            statement.close();
        }
        statement = driver.createStatement();

        var queryType = sqlParser.getTypeOfQuery(query);
        switch (queryType) {
            case SELECT -> {
                selectQuery(query);
                consoleLog.addNewLog(query);
            }
            case MODIFY -> {
                var updateCount = modifyDataQuery(query);
                consoleLog.addNewLog(query);
                consoleLog.addNewLog(String.format("%d rows affected", updateCount));
            }
            case DDL -> {
                ddlQuery(query);
                consoleLog.addNewLog(query);
                databaseStorage.addDatabase(driver.getDatabaseMeta());
            }
            case EXPLAIN_PLAN -> {
                consoleLog.addNewLog(query);
                explainPlanQuery(query);
            }
            case UNKNOWN -> throw new UnknownQueryTypeException();
        }
    }

    private void ddlQuery(String query) throws SQLException, QueryNotModifiableException {
        var isNotModifiable = statement.execute(query);
        if (!isNotModifiable)
            throw new QueryNotModifiableException();
    }

    private void selectQuery(String query) throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);
        selectResultPipe.load(resultSet);
    }

    // TODO надо понять, куда положить результат и написать эту энтити
    private int modifyDataQuery(String query) throws SQLException, QueryNotModifiableException {
        var isNotModifiable = statement.execute(query);
        int count;
        if (!isNotModifiable)
            return statement.getUpdateCount();
        else
            throw new QueryNotModifiableException();
    }

    // TODO надо понять, в какую энтити это сложить и как
    private void explainPlanQuery(String query) throws SQLException {
        var resultSet = statement.executeQuery(query);
        var root = explainResultSetToTree(resultSet, "Select", SQLDialect.POSTGRE);
        explainPlanResultPipe.pushItem(root);
    }
}
