package com.example.calmable.model;

public class FavModel {

    public String id;
    public String songName;
    public int imageView;
    public String url;
    private String isFav;

    public FavModel() {

    }

    public FavModel(String id, String songName, int imageView) {
        this.id = id;
        this.songName = songName;
        this.imageView = imageView;
    }

    public FavModel(String id, String songName, int imageView, String url, String isFav) {
        this.id = id;
        this.songName = songName;
        this.imageView = imageView;
        this.url = url;
        this.isFav = isFav;
    }

    public FavModel(String id, String songName, String url , int imageView) {
        this.id = id;
        this.songName = songName;
        this.imageView = imageView;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }
}
