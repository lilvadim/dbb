package ru.nsu.dbb.entity;

import java.util.HashMap;

public class DatabaseStorage {
    private final HashMap<String, Database> storage;

    DatabaseStorage() {
        storage = new HashMap<>();
    }

    public boolean addDatabase(Database database) {
        return storage.put(database.getName(), database) == null;
    }

    public Database getDatabase(String databaseName) {
        return storage.get(databaseName);
    }
}
