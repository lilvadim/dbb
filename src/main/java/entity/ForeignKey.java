package entity;

import java.util.HashMap;

public class ForeignKey {
    private HashMap<String, String> mapFKOnPK;
    private String PKTableName;
    private String FKTableName;

    public ForeignKey() {
        mapFKOnPK = new HashMap<>();
    }
    public void addMapping(String FK, String PK) {
        mapFKOnPK.put(FK, PK);
    }
    public String getPKTableName() {
        return PKTableName;
    }

    public void setPKTableName(String PKTableName) {
        this.PKTableName = PKTableName;
    }

    public String getFKTableName() {
        return FKTableName;
    }

    public void setFKTableName(String FKTableName) {
        this.FKTableName = FKTableName;
    }
}
