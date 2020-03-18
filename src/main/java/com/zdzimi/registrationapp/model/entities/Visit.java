package com.zdzimi.registrationapp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long visitId;
    private LocalTime visitTimeStart;

    @JsonIgnore
    private long visitTimeLength;

    private String visitorsEmail;
    private String visitorsName;
    private String visitorsSurname;

    @JsonIgnore
    @ManyToOne
    private DayTimetable dayTimetable;

    @ManyToOne
    private Place place;

    public Visit() {
    }

    public Visit(LocalTime visitTimeStart, long visitTimeLength, DayTimetable dayTimetable, Place place) {
        this.visitTimeStart = visitTimeStart;
        this.visitTimeLength = visitTimeLength;
        this.dayTimetable = dayTimetable;
        this.place = place;
    }

    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    public LocalTime getVisitTimeStart() {
        return visitTimeStart;
    }

    public void setVisitTimeStart(LocalTime visitTimeStart) {
        this.visitTimeStart = visitTimeStart;
    }

    public long getVisitTimeLength() {
        return visitTimeLength;
    }

    public void setVisitTimeLength(long visitTimeLength) {
        this.visitTimeLength = visitTimeLength;
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

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visit)) return false;
        Visit visit = (Visit) o;
        return getVisitId() == visit.getVisitId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVisitId());
    }
}
