package com.zdzimi.registrationapp.exception;

public class DayTimetableNotFoundException extends RuntimeException {

    public DayTimetableNotFoundException(int year, int month, int day) {
        super("Could not find timetable for " + month + "-" + year + "-" + day);
    }
}
