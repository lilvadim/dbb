package controller;

import java.sql.SQLException;

import driver.Driver;
import entity.Database;
import entity.DatabaseStorage;

public class SomeController {
    Driver driver;

    DatabaseStorage databaseStorage;
    public SomeController(Driver driver, DatabaseStorage databaseStorage) {
        this.driver = driver;
        this.databaseStorage = databaseStorage;
    }

    public void createDatabase(String databaseName, String url, String user, String password) {
        try {
            driver.openConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Database database = null;
        try {
            database = driver.getDatabaseMeta();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (database != null) {
            database.setName(databaseName);
            database.setPassword(password);
            database.setURL(url);
            database.setUser(user);
            if (!databaseStorage.addDatabase(database)) {
                System.out.println("DB exists");
            }
        } else {
            System.out.println("Some error");
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
