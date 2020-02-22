package com.pixelswordgames.fgdz.POJO;

public class Book {
    private int id;
    private String name;
    private String imageUrl;
    private String authors;
    private String type;
    private String publisher;
    private String url;

    public Book(){
        name = "";
        imageUrl = "";
        authors = "";
        url = "";
        type = "";
        publisher = "";
        id = 0;
    }

    public Book(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUrl(String url) {
        this.url = url;
        id = url.hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getType() {
        return type;
    }


    public String getName() {
        return name;
    }

    public String getAuthors() {
        return authors;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }
}
