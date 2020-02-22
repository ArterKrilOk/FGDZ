package com.pixelswordgames.fgdz.POJO;

public class Task {
    private int id;
    private String name;
    private String url;
    private String group;

    public Task(){}

    public Task(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
        id = url.hashCode();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
