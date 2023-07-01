package com.example.trainingcourse_application;

public class Admin {

    private String email;
    private String firstName;
    private String lastName;
    private String personalPhoto;

    public Admin(){

    }

    public Admin(String email,String firstName, String lastName, String personalPhoto){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalPhoto = personalPhoto;
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
}
