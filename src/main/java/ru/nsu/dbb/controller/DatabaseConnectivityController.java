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
            } else {
                Runnable refresher = () -> {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (databaseStorage.getDatabase(databaseName) != null) {
                            System.out.println(databaseName + " is refreshed");
                            refreshDatabase(databaseName);
                        } else {
                            break;
                        }
                    }
                };
                new Thread(refresher).start();
            }
        } else {
            throw new RuntimeException("Some error");
        }
    }

    public void refreshDatabase(String databaseName) {
        var currentDatabase = databaseStorage.getDatabase(databaseName);
        if (currentDatabase == null) {
            System.out.println("NULL!!");
            return;
        }
        try {
            driver.openConnection(
                    currentDatabase.getURL(),
                    currentDatabase.getUser(),
                    currentDatabase.getPassword()
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        try {
            driver.setDatabaseMeta(currentDatabase);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
