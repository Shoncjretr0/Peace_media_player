package com.example.peacemediaplayer;

import android.graphics.Bitmap;

class filemodel {



    private String duration;
    private String data;
    private String dispalyname;
    private String foldername;
    private String id;
    private Bitmap albumartt;



    // Constructor that is used to create an instance of the Movie object
    public filemodel(String foldername,String data,String dispalyname,String duration,String id,Bitmap albumartt) {
        this.foldername=foldername;
        this.duration=duration;
        this.data=data;
        this.dispalyname=dispalyname;
        this.id=id;
        this.albumartt=albumartt;

    }

    public Bitmap getAlbumartt() {
        return albumartt;
    }

    public void setAlbumartt(Bitmap albumartt) {
        this.albumartt = albumartt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDispalyname() {
        return dispalyname;
    }

    public void setDispalyname(String dispalyname) {
        this.dispalyname = dispalyname;
    }

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername;
    }
}
