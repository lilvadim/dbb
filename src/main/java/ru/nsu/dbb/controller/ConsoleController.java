package ru.nsu.dbb.controller;

import ru.nsu.dbb.driver.Driver;
import ru.nsu.dbb.entity.ConsoleLog;
import ru.nsu.dbb.entity.DatabaseStorage;
import ru.nsu.dbb.exceptions.QueryNotModifiableException;
import ru.nsu.dbb.exceptions.UnknownQueryTypeException;
import ru.nsu.dbb.sql.SqlParser;

import javax.inject.Inject;
import java.sql.SQLException;

public class ConsoleController {
    private final Driver driver;
    private final SqlParser sqlParser;

    private final DatabaseStorage databaseStorage;

    private final ConsoleLog consoleLog;

    @Inject
    public ConsoleController(Driver driver, SqlParser sqlParser, DatabaseStorage databaseStorage, ConsoleLog consoleLog) {
        this.driver = driver;
        this.sqlParser = sqlParser;
        this.databaseStorage = databaseStorage;
        this.consoleLog = consoleLog;
    }

    public void runQuery(String query) throws UnknownQueryTypeException, QueryNotModifiableException, SQLException {
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
                databaseStorage.addDatabase(driver.getDatabaseMeta());
            }
            case EXPLAIN_PLAN -> {
                explainPlanQuery(query);
            }
            case UNKNOWN -> throw new UnknownQueryTypeException();
        }
    }

    private void ddlQuery(String query) throws SQLException, QueryNotModifiableException {
        try (var statement = driver.createStatement()){
            var isNotModifiable = statement.execute(query);
            if (!isNotModifiable)
                throw new QueryNotModifiableException();
        }
    }
    // TODO надо создать сущности для селекта и положить туда результат
    private void selectQuery(String query) {

    }
    // TODO надо понять, куда положить результат и написать эту энтити
    private int modifyDataQuery(String query) throws SQLException, QueryNotModifiableException {
        try (var statement = driver.createStatement()){
            var isNotModifiable = statement.execute(query);
            int count;
            if (!isNotModifiable)
                return statement.getUpdateCount();
            else
                throw new QueryNotModifiableException();
        }
    }

    // TODO надо понять, в какую энтити это сложить и как
    private void explainPlanQuery(String query) {

    }
}
