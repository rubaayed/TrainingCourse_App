package com.example.trainingcourse_application;

public class Section {

    private int sectionId;
    private String email;
    private int courseId;
    private int regDeadLine;
    private int startDate;
    private String schedule;
    private String venue;

    public Section(){

    }

    public Section(int sectionId, String email, int courseId, int regDeadLine, int startDate, String schedule, String venue){
        this.sectionId = sectionId;
        this.email = email;
        this.courseId = courseId;
        this.regDeadLine = regDeadLine;
        this.startDate = startDate;
        this.schedule = schedule;
        this.venue = venue;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getRegDeadLine() {
        return regDeadLine;
    }

    public void setRegDeadLine(int regDeadLine) {
        this.regDeadLine = regDeadLine;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
