package ru.nsu.dbb.controller;

import ru.nsu.dbb.exceptions.QueryNotModifiableException;
import ru.nsu.dbb.exceptions.UnknownQueryTypeException;
import ru.nsu.dbb.driver.Driver;
import ru.nsu.dbb.entity.DatabaseStorage;
import ru.nsu.dbb.sql.SqlParser;

import java.sql.SQLException;

public class ConsoleController {
    private final Driver driver;
    private final SqlParser sqlParser;

    private DatabaseStorage databaseStorage;

    public ConsoleController(Driver driver, SqlParser sqlParser, DatabaseStorage databaseStorage) {
        this.driver = driver;
        this.sqlParser = sqlParser;
        this.databaseStorage = databaseStorage;
    }

    public void runQuery(String query) throws UnknownQueryTypeException, QueryNotModifiableException, SQLException {
        var queryType = sqlParser.getTypeOfQuery(query);
        switch (queryType) {
            case SELECT -> {
                selectQuery(query);
            }
            case MODIFY -> {
                modifyDataQuery(query);
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
    private void modifyDataQuery(String query) throws SQLException, QueryNotModifiableException {
        try (var statement = driver.createStatement()){
            var isNotModifiable = statement.execute(query);
            int count;
            if (!isNotModifiable)
                count = statement.getUpdateCount();
            else
                throw new QueryNotModifiableException();
        }
    }

    // TODO надо понять, в какую энтити это сложить и как
    private void explainPlanQuery(String query) {

    }

    public void setDatabaseStorage(DatabaseStorage databaseStorage) {
        this.databaseStorage = databaseStorage;
    }
}
