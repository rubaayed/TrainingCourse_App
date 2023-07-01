package com.example.trainingcourse_application;

public class Student {

    private String email;
    private String firstName;
    private String lastName;
    private String personalPhoto;
    private String mobile;
    private String address;

    public Student(){

    }

    public Student(String email,String firstName, String lastName, String personalPhoto,
                   String mobile, String address){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalPhoto = personalPhoto;
        this.mobile = mobile;
        this.address = address;
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
}
