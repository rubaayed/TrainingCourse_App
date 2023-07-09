package com.example.trainingcourse_application;

import android.content.Context;

public class InstructorHomeEmail {

    private static String emailAddress;

    private InstructorHomeEmail(){
    }

    public static void setEmailAddress(String email) {
        emailAddress = email;
    }

    public static String getEmailAddress() {
        return emailAddress;
    }
}
