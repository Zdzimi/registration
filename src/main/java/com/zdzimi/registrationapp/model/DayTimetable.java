package com.zdzimi.registrationapp.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DayTimetable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dayTimetableId;

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
}
