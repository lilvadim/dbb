package ru.nsu.dbb.entity.select;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;

public class ResultTable {

    private final List<ResultColumn> columns = new ArrayList<>();
    private final ObservableList<ResultRow> rows = FXCollections.observableArrayList();

    public List<ResultColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ResultColumn> columns) {
        this.columns.clear();
        this.columns.addAll(columns);
    }

    public void addColumn(ResultColumn column) {
        columns.add(column);
    }

    public ObservableList<ResultRow> getRows() {
        return rows;
    }

    public void setRows(List<ResultRow> rows) {
        this.rows.clear();
        this.rows.addAll(rows);
    }

    public void addRows(List<ResultRow> rows) {
        this.rows.addAll(rows);
    }

}
