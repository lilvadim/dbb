package ru.nsu.dbb.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Column {
    private final StringProperty name = new SimpleStringProperty();
    // todo сделать на enum
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty size = new SimpleStringProperty();

    private final StringProperty isNullable = new SimpleStringProperty();

    public Column(String name, String type, String size, String isNullable) {
        setName(name);
        setType(type);
        setSize(size);
        setIsNullable(isNullable);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getSize() {
        return size.get();
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public StringProperty sizeProperty() {
        return size;
    }

    public String getIsNullable() {
        return isNullable.get();
    }

    public void setIsNullable(String isNullable) {
        this.isNullable.set(isNullable);
    }

    public StringProperty isNullableProperty() {
        return isNullable;
    }
}
