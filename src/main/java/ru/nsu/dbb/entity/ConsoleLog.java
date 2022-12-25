package ru.nsu.dbb.entity;

import java.util.ArrayList;

public class ConsoleLog {
    ArrayList<String> logs;

    public ConsoleLog() {
        logs = new ArrayList<>();
    }

    public void addNewLog(String log) {
        logs.add(log);
    }
}
