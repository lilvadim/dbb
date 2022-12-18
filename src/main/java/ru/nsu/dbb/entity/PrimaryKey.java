package ru.nsu.dbb.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PrimaryKey {

    private final StringProperty primaryKeyName = new SimpleStringProperty();
    private final ObservableList<String> primaryKeyColumns = FXCollections.observableArrayList();

    public void addColumnToPrimaryKey(String column) {
        primaryKeyColumns.add(column);
    }

    public ObservableList<String> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName.get();
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName.set(primaryKeyName);
    }

    public StringProperty primaryKeyNameProperty() {
        return primaryKeyName;
    }
}
