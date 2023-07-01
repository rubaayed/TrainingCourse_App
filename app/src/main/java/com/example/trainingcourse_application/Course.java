package com.example.trainingcourse_application;

public class Course {

    private int id;
    private String title;
    private String mainTopics;
    private String image;

    public Course(){

    }

    public Course(int id, String title, String mainTopics, String image){
        this.id = id;
        this.title = title;
        this.mainTopics = mainTopics;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainTopics() {
        return mainTopics;
    }

    public void setMainTopics(String mainTopics) {
        this.mainTopics = mainTopics;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
