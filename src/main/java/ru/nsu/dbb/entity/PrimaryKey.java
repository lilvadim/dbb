package ru.nsu.dbb.entity;

import java.util.ArrayList;

public class PrimaryKey {

    private String primaryKeyName;
    private final ArrayList<String> primaryKeyColumns;

    public PrimaryKey() {
        primaryKeyColumns = new ArrayList<>();
    }

    public void addColumnToPrimaryKey(String column) {
        primaryKeyColumns.add(column);
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }
}
