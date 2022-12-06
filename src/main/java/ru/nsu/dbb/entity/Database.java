package ru.nsu.dbb.entity;

import java.util.ArrayList;

public class Database {
    private String name;
    private String URL;
    private String user;
    private String password;

    private ArrayList<Catalog> catalogs;

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

    public ArrayList<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(ArrayList<Catalog> catalogs) {
        this.catalogs = catalogs;
    }
}
