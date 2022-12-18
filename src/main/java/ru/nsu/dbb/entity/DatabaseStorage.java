package ru.nsu.dbb.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class DatabaseStorage {
    private final ObservableMap<String, Database> storage;

    DatabaseStorage() {
        storage = FXCollections.observableHashMap();
    }

    public boolean addDatabase(Database database) {
        return storage.put(database.getName(), database) == null;
    }

    public Database getDatabase(String databaseName) {
        return storage.get(databaseName);
    }

    public ObservableMap<String, Database> getStorage() {
        return storage;
    }
}
