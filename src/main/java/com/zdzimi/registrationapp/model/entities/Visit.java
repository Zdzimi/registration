package com.zdzimi.registrationapp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Visit extends EntityModel {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long visitId;
    private LocalTime visitTimeStart;

    @JsonIgnore
    private long visitTimeLength;

    @ManyToOne
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
