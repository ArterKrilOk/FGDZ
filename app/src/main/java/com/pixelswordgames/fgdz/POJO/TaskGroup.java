package com.pixelswordgames.fgdz.POJO;

import java.util.ArrayList;
import java.util.List;

public class TaskGroup {
    private int id;
    private String name;
    private List<Task> tasks;
    private boolean opened;

    public TaskGroup(){
        tasks = new ArrayList<>();
        opened = false;
    }

    public TaskGroup(String name){
        this.name = name;
        id = name.hashCode();
        tasks = new ArrayList<>();
        opened = false;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void setName(String name) {
        this.name = name;
        id = name.hashCode();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isOpened() {
        return opened;
    }
}
