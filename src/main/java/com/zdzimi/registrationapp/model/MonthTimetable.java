package com.zdzimi.registrationapp.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MonthTimetable {

    @Id
    @GeneratedValue
    private long timetableId;

    private int year;
    private int mounth;

    @OneToMany(mappedBy = "monthTimetable")
    private Set<DayTimetable> dayTimetableSet = new HashSet<>();

    @ManyToOne
    private Representative representative;

    public long getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(long timetableId) {
        this.timetableId = timetableId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMounth() {
        return mounth;
    }

    public void setMounth(int mounth) {
        this.mounth = mounth;
    }

    public Set<DayTimetable> getDayTimetableSet() {
        return dayTimetableSet;
    }

    public void setDayTimetableSet(Set<DayTimetable> dayTimetableSet) {
        this.dayTimetableSet = dayTimetableSet;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }
}
