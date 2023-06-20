package ru.nsu.dbb.response;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import ru.nsu.dbb.entity.select.ResultTable;
import ru.nsu.dbb.entity.select.ResultColumn;
import ru.nsu.dbb.entity.select.ResultRow;

public class SelectResultPipe {

    private final int PAGE_SIZE = 512;

    private ResultSet resultSet;
    private final ObjectProperty<ResultTable> observable = new SimpleObjectProperty<>();

    public ObservableObjectValue<ResultTable> getObservable() {
        return observable;
    }

    public void load(ResultSet resultSet) throws SQLException {
        if (this.resultSet != null) {
            this.resultSet.close();
        }
        this.resultSet = resultSet;

        ResultTable resultTable = new ResultTable();
        ResultSetMetaData metadata = resultSet.getMetaData();
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            resultTable.addColumn(new ResultColumn(
                metadata.getColumnType(i),
                metadata.getColumnTypeName(i),
                metadata.getColumnName(i),
                metadata.getColumnLabel(i)
            ));
        }
        observable.set(resultTable);

        loadMoreRows();
    }

    public int loadMoreRows() throws SQLException {
        if (resultSet == null || resultSet.isClosed()) {
            return 0;
        }

        List<ResultRow> newRows = new ArrayList<>();
        for (int i = 0; i < PAGE_SIZE && resultSet.next(); i++) {
            ResultRow row = new ResultRow();
            for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
                row.add(resultSet.getString(j));
            }
            newRows.add(row);
        }
        observable.get().addRows(newRows);
        return newRows.size();
    }

}
