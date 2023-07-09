package com.example.trainingcourse_application;

public class Section {

    private int sectionId;
    private String email;
    private int courseId;
    private long regDeadLine;
    private long startDate;
    private String schedule;
    private String venue;
    private long endDate;

    public Section(){

    }

    public Section(int sectionId, String email, int courseId, long regDeadLine, long startDate, String schedule, String venue,long endDate){
        this.sectionId = sectionId;
        this.email = email;
        this.courseId = courseId;
        this.regDeadLine = regDeadLine;
        this.startDate = startDate;
        this.schedule = schedule;
        this.venue = venue;
        this.endDate = endDate;
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

    public long getRegDeadLine() {
        return regDeadLine;
    }

    public void setRegDeadLine(long regDeadLine) {
        this.regDeadLine = regDeadLine;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
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
