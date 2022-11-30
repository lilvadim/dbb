package ru.nsu.entity;

public class Index {
    private final String name;
    private final boolean nonUnique;
    private final String column;

    public Index(String name, boolean nonUnique, String column) {
        this.name = name;
        this.nonUnique = nonUnique;
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public boolean isNonUnique() {
        return nonUnique;
    }

    public String getColumn() {
        return column;
    }
}

