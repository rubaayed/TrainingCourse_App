package com.example.trainingcourse_application;

import android.content.Context;

public class StudentHomeEmail {

    private static String emailAddress;

    private StudentHomeEmail(){
    }

    public static void setEmailAddress(String email) {
        emailAddress = email;
    }

    public static String getEmailAddress() {
        return emailAddress;
    }
}
