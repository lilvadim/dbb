package ru.nsu.dbb.controller;

import javafx.application.Platform;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.nsu.dbb.driver.Driver;
import ru.nsu.dbb.entity.Database;
import ru.nsu.dbb.entity.DatabaseStorage;

import javax.inject.Inject;
import java.sql.SQLException;

@RequiredArgsConstructor(onConstructor_ = {@Inject})
@Getter
public class DatabaseConnectivityController {
    private final Driver driver;

    private final DatabaseStorage databaseStorage;

    public void createDatabase(String databaseName, String url, String user, String password) throws SQLException {
        driver.openConnection(url, user, password);
        Database database = driver.getDatabaseMeta();
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
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (databaseStorage.getDatabase(databaseName) != null) {
                            System.out.println(databaseName + " is refreshed");
                            Platform.runLater(() -> refreshDatabase(databaseName));
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
            driver.updateMetaForDatabase(currentDatabase);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
