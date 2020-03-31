package com.zdzimi.registrationapp.model.template;

import org.springframework.hateoas.EntityModel;

import java.util.List;

public class Template extends EntityModel {

    private YearAndMonth yearAndMonth;
    private List<Day> days;
    private long visitTime;

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

    public long getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(long visitTime) {
        this.visitTime = visitTime;
    }
}
