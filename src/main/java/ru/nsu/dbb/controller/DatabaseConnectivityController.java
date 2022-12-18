package ru.nsu.dbb.controller;

import ru.nsu.dbb.driver.Driver;
import ru.nsu.dbb.entity.Database;
import ru.nsu.dbb.entity.DatabaseStorage;

import javax.inject.Inject;
import java.sql.SQLException;

public class DatabaseConnectivityController {
    private final Driver driver;

    private final DatabaseStorage databaseStorage;

    @Inject
    public DatabaseConnectivityController(Driver driver, DatabaseStorage databaseStorage) {
        this.driver = driver;
        this.databaseStorage = databaseStorage;
    }

    public void createDatabase(String databaseName, String url, String user, String password) throws SQLException {
        driver.openConnection(url, user, password);
        Database database;
        database = driver.getDatabaseMeta();
        if (database != null) {
            database.setName(databaseName);
            database.setPassword(password);
            database.setURL(url);
            database.setUser(user);
            if (!databaseStorage.addDatabase(database)) {
                System.out.println("DB exists");
            }
        } else {
            throw new RuntimeException("Some error");
        }
    }

    public void refreshDatabase(String databaseName) {
        var currentDatabase = databaseStorage.getDatabase(databaseName);
        try {
            driver.openConnection(
                    currentDatabase.getURL(),
                    currentDatabase.getUser(),
                    currentDatabase.getPassword()
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        Database database = null;
        try {
            database = driver.getDatabaseMeta();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if (database != null) {
            database.setName(databaseName);
            if (!databaseStorage.addDatabase(database)) {
                System.out.println("DB updated");
            }
        } else {
            System.out.println("Some error");
        }
    }
}
