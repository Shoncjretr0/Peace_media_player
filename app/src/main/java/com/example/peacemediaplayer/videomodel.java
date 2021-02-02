package com.example.peacemediaplayer;

public class videomodel {

    private String Name;

    private String path;

    private Integer count;

    public videomodel(String Name, String path,Integer count) {
        this.Name = Name;
        this.path = path;
        this.count=count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
