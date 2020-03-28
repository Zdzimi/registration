package com.zdzimi.registrationapp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zdzimi.registrationapp.model.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Representative extends User{

    @JsonIgnore
    @ManyToMany
    private Set<Institution> workPlaces = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "representative")
    private Set<MonthTimetable> monthTimetables = new HashSet<>();

    public Representative() {
    }

    public Representative(String username, String surname, String email, String password, Role role) {
        super(username, surname, email, password, role);
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
