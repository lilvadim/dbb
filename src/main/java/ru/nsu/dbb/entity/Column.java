package ru.nsu.dbb.entity;

public class Column {
    private String name;
    // todo сделать на enum
    private String type;
    private String size;

    private String isNullable;

    public Column(String name, String type, String size, String isNullable) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.isNullable = isNullable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }
}
