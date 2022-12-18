package ru.nsu.dbb.entity;

import java.util.HashMap;

public class ForeignKey {
    private final HashMap<String, String> mapFKOnPK;
    private String primaryKeyTableName;
    private String foreignKeyTableName;

    public ForeignKey() {
        mapFKOnPK = new HashMap<>();
    }

    public void addMapping(String FK, String PK) {
        mapFKOnPK.put(FK, PK);
    }

    public String getPrimaryKeyTableName() {
        return primaryKeyTableName;
    }

    public void setPrimaryKeyTableName(String primaryKeyTableName) {
        this.primaryKeyTableName = primaryKeyTableName;
    }

    public String getForeignKeyTableName() {
        return foreignKeyTableName;
    }

    public void setForeignKeyTableName(String foreignKeyTableName) {
        this.foreignKeyTableName = foreignKeyTableName;
    }
}
