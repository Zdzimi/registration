package com.zdzimi.registrationapp.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long visitId;
    private LocalTime visitTime;

    private String visitorsEmail;
    private String visitorsName;
    private String visitorsSurname;

    @ManyToOne
    private DayTimetable dayTimetable;

    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    public LocalTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalTime visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitorsEmail() {
        return visitorsEmail;
    }

    public void setVisitorsEmail(String visitorsEmail) {
        this.visitorsEmail = visitorsEmail;
    }

    public String getVisitorsName() {
        return visitorsName;
    }

    public void setVisitorsName(String visitorsName) {
        this.visitorsName = visitorsName;
    }

    public String getVisitorsSurname() {
        return visitorsSurname;
    }

    public void setVisitorsSurname(String visitorsSurname) {
        this.visitorsSurname = visitorsSurname;
    }

    public DayTimetable getDayTimetable() {
        return dayTimetable;
    }

    public void setDayTimetable(DayTimetable dayTimetable) {
        this.dayTimetable = dayTimetable;
    }
}
