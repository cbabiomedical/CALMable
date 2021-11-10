package com.example.calmable.model;

public class MusicModel {

    public String songName ;
    public String url ;

    public MusicModel() {
    }

    public MusicModel(String songName, String url) {
        this.songName = songName;
        this.url = url;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
