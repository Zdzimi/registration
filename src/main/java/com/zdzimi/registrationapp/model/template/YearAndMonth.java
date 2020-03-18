package com.zdzimi.registrationapp.model.template;

public class YearAndMonth {

    private int year;
    private int month;

    public YearAndMonth() {
    }

    public YearAndMonth(int year, int month) {
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
}
