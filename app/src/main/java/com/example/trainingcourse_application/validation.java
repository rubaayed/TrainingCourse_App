package com.example.trainingcourse_application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
    private static final Pattern patternPass = Pattern.compile(PASSWORD_PATTERN);

    private static String datePattern = "dd/MM/yyyy";


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

    public static boolean checkTitle(String title){
        if(title.length() > 32 || title == null || title.length() == 0)
            return false;
        return true;
    }

    public static boolean checkMainTopics(String mainTopics){
        if(mainTopics == null || mainTopics.length() == 0)
            return false;
        return true;
    }

    public static boolean checkDateFormat(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean checkTimeFormat(String timeString){
        String regexPattern = "\\d{2}:\\d{2}-\\d{2}:\\d{2}";
        if (!timeString.matches(regexPattern)) {
            return false; // Not in the correct format
        }

        String[] timeParts = timeString.split("-");
        String startTime = timeParts[0];
        String endTime = timeParts[1];

        if (startTime.compareTo(endTime) >= 0) {
            return false; // Start time is greater than or equal to end time
        }

        // Check if the time is within the real time ranges
        String[] startParts = startTime.split(":");
        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);

        String[] endParts = endTime.split(":");
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);

        if (startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23 ||
                startMinute < 0 || startMinute > 59 || endMinute < 0 || endMinute > 59) {
            return false; // Invalid hour or minute value
        }

        return true;
    }

    public static boolean checkVenue(String venue){
        if(venue.length() > 32 || venue == null || venue.length() == 0)
            return false;
        return true;
    }
}
