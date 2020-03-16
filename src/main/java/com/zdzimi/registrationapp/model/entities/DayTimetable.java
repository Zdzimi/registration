package com.zdzimi.registrationapp.model.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class DayTimetable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dayTimetableId;
    private int dayOfMonth;

    @OneToMany(mappedBy = "dayTimetable")
    private Set<Visit> visits = new HashSet<>();

    @ManyToOne
    private MonthTimetable monthTimetable;

    public long getDayTimetableId() {
        return dayTimetableId;
    }

    public void setDayTimetableId(long dayTimetableId) {
        this.dayTimetableId = dayTimetableId;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public MonthTimetable getMonthTimetable() {
        return monthTimetable;
    }

    public void setMonthTimetable(MonthTimetable monthTimetable) {
        this.monthTimetable = monthTimetable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayTimetable)) return false;
        DayTimetable that = (DayTimetable) o;
        return getDayTimetableId() == that.getDayTimetableId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDayTimetableId());
    }
}
