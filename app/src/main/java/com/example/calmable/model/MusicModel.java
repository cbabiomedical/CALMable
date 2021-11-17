package com.example.calmable.model;

public class MusicModel {

    public String id ;
    public String songName ;
    public int imageView;
    public String url ;
    private String isFav;

    public MusicModel() {
    }

    public MusicModel(String id, String songName, String url, int imageView) {
        this.id = id;
        this.songName = songName;
        this.url = url;
        this.imageView = imageView;
    }

    public MusicModel(String id, String songName, String url, int imageView, String isFav) {
        this.id = id;
        this.songName = songName;
        this.url = url;
        this.imageView = imageView;
        this.isFav = isFav;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "id='" + id + '\'' +
                ", songName='" + songName + '\'' +
                ", imageView=" + imageView +
                ", url='" + url + '\'' +
                ", isFav='" + isFav + '\'' +
                '}';
    }
}
