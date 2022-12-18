package ru.nsu.dbb.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Schema {
    private final StringProperty name = new SimpleStringProperty();
    private final ObservableList<Table> tables = FXCollections.observableArrayList();

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObservableList<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables.setAll(tables);
    }
}
