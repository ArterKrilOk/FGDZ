package com.pixelswordgames.fgdz.POJO;

import java.util.ArrayList;
import java.util.List;

public class Decision {
    private int id;
    private String url;
    private List<Image> images;

    public Decision(){
        images = new ArrayList<>();
    }

    public Decision(String url){
        this.url = url;
        id = url.hashCode();
        images = new ArrayList<>();
    }

    public void setUrl(String url) {
        this.url = url;
        id=  url.hashCode();
    }

    public void setIamges(List<Image> images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public void addImage(Image image){
        images.add(image);
    }


    public List<Image> getImages(){
        return images;
    }

}

