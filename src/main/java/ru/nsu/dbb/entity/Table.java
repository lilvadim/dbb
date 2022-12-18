package ru.nsu.dbb.entity;

import java.util.ArrayList;

public class Table {
    private final String name;
    private ArrayList<Column> columns;
    private ArrayList<PrimaryKey> primaryKeys;
    private ArrayList<ForeignKey> foreignKeys;

    private ArrayList<Index> indexes;

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(ArrayList<Index> indexes) {
        this.indexes = indexes;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }

    public ArrayList<PrimaryKey> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(ArrayList<PrimaryKey> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public ArrayList<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(ArrayList<ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
}