package ru.nsu.dbb.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConsoleLog {
    ObservableList<String> logs = FXCollections.observableArrayList();

    public void addNewLog(String log) {
        logs.add(log);
    }

    public ObservableList<String> getLogs() {
        return logs;
    }
}
