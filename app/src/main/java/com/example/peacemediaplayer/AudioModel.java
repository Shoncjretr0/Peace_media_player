package com.example.peacemediaplayer;
class AudioModel {

    // Store the id of the  movie poster

    // Store the name of the movie
    private String mName;
    // Store the release date of the movie
    private String mid;

    private String duration;
    private String artist;
    private String data;
    private String dispalyname;




    // Constructor that is used to create an instance of the Movie object
    public AudioModel(String mid,String artist, String mName,String data,String dispalyname,String duration) {
        this.mName = mName;
        this.mid = mid;
        this.duration=duration;
        this.artist=artist;
        this.data=data;
        this.dispalyname=dispalyname;


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

    public String getMid() {
        return mid;
    }

    public String getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }



    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }


}
