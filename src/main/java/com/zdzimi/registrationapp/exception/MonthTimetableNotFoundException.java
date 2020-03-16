package com.zdzimi.registrationapp.exception;

public class MonthTimetableNotFoundException extends RuntimeException {

    public MonthTimetableNotFoundException(int year, int month) {
        super("Could not find timetable for " + month + "-" + year);
    }
}
