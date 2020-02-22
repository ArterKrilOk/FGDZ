package com.pixelswordgames.fgdz.POJO;

import android.graphics.Bitmap;

public class Image {
    private int id;
    private String url;
    private String alt;
    private Bitmap bitmap;

    public Image(){ }

    public Image(String url, String alt, Bitmap bitmap) {
        this.url = url;
        this.alt = alt;
        this.bitmap = bitmap;
        id = url.hashCode();
    }

    public void setUrl(String url) {
        this.url = url;
        id = url.hashCode();
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getId() {
        return id;
    }

    public String getAlt() {
        return alt;
    }

    public String getUrl() {
        return url;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
