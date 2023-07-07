package com.example.trainingcourse_application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
    private static final Pattern patternPass = Pattern.compile(PASSWORD_PATTERN);


    public static boolean checkEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkFirstName(String name){
        if(name.length() < 3 || name.length() > 20)
            return false;
        return true;
    }

    public static boolean checkLastName(String name){
        if(name.length() < 3 || name.length() > 20)
            return false;
        return true;
    }

    public static boolean checkPassword(String password){
        Matcher matcher = patternPass.matcher(password);
        return matcher.matches();
    }

    public static boolean checkMobile(String mobile){
        if(!mobile.matches("\\d+") || mobile.length() != 10)
            return false;
        return true;
    }

    public static boolean checkAddress(String address){
        if(address.length() > 32 || address == null || address.length() == 0)
            return false;
        return true;
    }

    public static boolean checkSpecialization(String specialization){
        if(specialization.length() > 32 || specialization == null || specialization.length() == 0)
            return false;
        return true;
    }
}
