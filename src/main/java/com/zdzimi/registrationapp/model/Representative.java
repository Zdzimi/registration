package com.zdzimi.registrationapp.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Representative extends User{

    @ManyToMany
    private Set<Institution> workPlaces = new HashSet<>();

    @OneToMany(mappedBy = "representative")
    private Set<MonthTimetable> monthTimetables = new HashSet<>();

    public Representative() {
    }

    public Representative(String username, String email, String password, Role role) {
        super(username, email, password, role);
    }

    public Set<Institution> getWorkPlaces() {
        return workPlaces;
    }

    public void setWorkPlaces(Set<Institution> workPlaces) {
        this.workPlaces = workPlaces;
    }

    public Set<MonthTimetable> getMonthTimetables() {
        return monthTimetables;
    }

    public void setMonthTimetables(Set<MonthTimetable> monthTimetables) {
        this.monthTimetables = monthTimetables;
    }
}
