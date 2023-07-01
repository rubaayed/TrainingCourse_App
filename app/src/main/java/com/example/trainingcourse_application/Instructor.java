package com.example.trainingcourse_application;

import java.util.ArrayList;

public class Instructor {

    private String email;
    private String firstName;
    private String lastName;
    private String personalPhoto;
    private String mobile;
    private String address;
    private String specialization;
    private String degree;
    private ArrayList<String> coursesCanTeach=new ArrayList<String>();


    public Instructor(){

    }

    public Instructor(String email,String firstName, String lastName, String personalPhoto,
                      String mobile, String address, String specialization, String degree,ArrayList<String> coursesCanTeach){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalPhoto = personalPhoto;
        this.mobile = mobile;
        this.address = address;
        this.specialization = specialization;
        this.degree = degree;
        this.coursesCanTeach = coursesCanTeach;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalPhoto() {
        return personalPhoto;
    }

    public void setPersonalPhoto(String personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public ArrayList<String> getCoursesCanTeach() {
        return coursesCanTeach;
    }

    public void setCoursesCanTeach(ArrayList<String> coursesCanTeach) {
        this.coursesCanTeach = coursesCanTeach;
    }
}
