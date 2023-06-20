package ru.nsu.dbb.entity.select;

import java.util.ArrayList;
import java.util.List;

public class ResultRow {

    private final List<Object> values = new ArrayList<>();

    public Object get(int i) {
        return values.get(i);
    }

    public void add(Object value) {
        values.add(value);
    }

}
