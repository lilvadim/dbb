package ru.nsu.dbb.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class ForeignKey {
    private final ObservableMap<String, String> mapForeignKeyToPrimaryKey = FXCollections.observableHashMap();
    private final StringProperty primaryKeyTableName = new SimpleStringProperty();
    private final StringProperty foreignKeyTableName = new SimpleStringProperty();

    public void addMapping(String FK, String PK) {
        mapForeignKeyToPrimaryKey.put(FK, PK);
    }

    public String getPrimaryKeyTableName() {
        return primaryKeyTableName.get();
    }

    public void setPrimaryKeyTableName(String primaryKeyTableName) {
        this.primaryKeyTableName.set(primaryKeyTableName);
    }

    public StringProperty primaryKeyTableNameProperty() {
        return primaryKeyTableName;
    }

    public String getForeignKeyTableName() {
        return foreignKeyTableName.get();
    }

    public void setForeignKeyTableName(String foreignKeyTableName) {
        this.foreignKeyTableName.set(foreignKeyTableName);
    }

    public StringProperty foreignKeyTableNameProperty() {
        return foreignKeyTableName;
    }

    public ObservableMap<String, String> getMapForeignKeyToPrimaryKey() {
        return mapForeignKeyToPrimaryKey;
    }
}
