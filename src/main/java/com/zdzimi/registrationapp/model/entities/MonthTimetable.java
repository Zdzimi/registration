package com.zdzimi.registrationapp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class MonthTimetable {

    @Id
    @GeneratedValue
    private long timetableId;

    private int year;
    private int month;

    @OneToMany(mappedBy = "monthTimetable")
    private Set<DayTimetable> dayTimetableSet = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    private Representative representative;

    @JsonIgnore
    @ManyToOne
    private Institution institution;

    public MonthTimetable() {
    }

    public MonthTimetable(int year, int month, Representative representative, Institution institution) {
        this.year = year;
        this.month = month;
        this.representative = representative;
        this.institution = institution;
    }

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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonthTimetable)) return false;
        MonthTimetable that = (MonthTimetable) o;
        return getTimetableId() == that.getTimetableId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimetableId());
    }
}
