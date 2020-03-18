package com.zdzimi.registrationapp.model;

import com.zdzimi.registrationapp.model.entities.Visit;

import java.util.ArrayList;
import java.util.List;

public class DayTimetableAndErrors {

    private int dayOfMonth;
    private List<Visit> visits = new ArrayList<>();
    private List<String> errors = new ArrayList<>();

    public DayTimetableAndErrors() {
    }

    public DayTimetableAndErrors(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
