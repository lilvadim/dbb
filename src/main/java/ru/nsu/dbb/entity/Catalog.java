package ru.nsu.dbb.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Catalog {
    private final StringProperty name = new SimpleStringProperty();
    private final ObservableList<Schema> schemas = FXCollections.observableArrayList();

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<Schema> schemas) {
        this.schemas.setAll(schemas);
    }

    public StringProperty nameProperty() {
        return name;
    }
}
