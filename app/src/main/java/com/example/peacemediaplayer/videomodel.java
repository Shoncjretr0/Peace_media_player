package com.example.peacemediaplayer;

public class videomodel {

    private String Name;

    private String path;

    public videomodel(String Name, String path) {
        this.Name = Name;
        this.path = path;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = Name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
