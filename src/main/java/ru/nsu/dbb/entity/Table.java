package ru.nsu.dbb.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Table {
    private final StringProperty name = new SimpleStringProperty();
    private final ObservableList<Column> columns = FXCollections.observableArrayList();
    private final ObservableList<PrimaryKey> primaryKeys = FXCollections.observableArrayList();
    private final ObservableList<ForeignKey> foreignKeys = FXCollections.observableArrayList();
    private final ObservableList<Index> indexes = FXCollections.observableArrayList();

    public Table(String name) {
        setName(name);
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

    public ObservableList<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns.setAll(columns);
    }

    public ObservableList<PrimaryKey> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<PrimaryKey> primaryKeys) {
        this.primaryKeys.setAll(primaryKeys);
    }

    public ObservableList<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        this.foreignKeys.setAll(foreignKeys);
    }

    public ObservableList<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes.setAll(indexes);
    }
}