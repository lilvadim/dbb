package entity;

import java.util.ArrayList;

public class Database {
    private String name;
    private String URL;
    private String user;
    private String password;

    private ArrayList<CataLog> cataLogs;

    public Database() {
    }


    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getURL() {
        return URL;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CataLog> getCatalogs() {
        return cataLogs;
    }

    public void setCatalogs(ArrayList<CataLog> cataLogs) {
        this.cataLogs = cataLogs;
    }
}
