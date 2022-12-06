package ru.nsu.dbb.entity;

import java.util.ArrayList;

public class Catalog {
    private String name;
    private ArrayList<Schema> schemas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(ArrayList<Schema> schemas) {
        this.schemas = schemas;
    }
}
