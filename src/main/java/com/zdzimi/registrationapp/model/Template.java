package com.zdzimi.registrationapp.model;

import java.util.List;

public class Template {

    private YearAndMonth yearAndMonth;
    private List<Day> days;

    public Template() {
    }

    public Template(YearAndMonth yearAndMonth, List<Day> days) {
        this.yearAndMonth = yearAndMonth;
        this.days = days;
    }

    public YearAndMonth getYearAndMonth() {
        return yearAndMonth;
    }

    public void setYearAndMonth(YearAndMonth yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
