package com.zdzimi.registrationapp.model;

import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;

public class MonthTimetableAndErrors extends EntityModel {

    private int year;
    private int month;
    private List<DayTimetableAndErrors> dayTimetableAndErrors = new ArrayList<>();

    public MonthTimetableAndErrors() {
    }

    public MonthTimetableAndErrors(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<DayTimetableAndErrors> getDayTimetableAndErrors() {
        return dayTimetableAndErrors;
    }

    public void setDayTimetableAndErrors(List<DayTimetableAndErrors> dayTimetableAndErrors) {
        this.dayTimetableAndErrors = dayTimetableAndErrors;
    }
}
