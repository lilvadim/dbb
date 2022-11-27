package ru.nsu.entity;

import java.util.ArrayList;

public class Table {
    private String name;
    private ArrayList<Column> columns;
    private ArrayList<PrimaryKey> primaryKeys;
    private ArrayList<ForeignKey> foreignKeys;

    public Table(String name) {
        this.name = name;
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
