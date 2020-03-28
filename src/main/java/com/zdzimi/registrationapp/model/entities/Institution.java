package com.zdzimi.registrationapp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Institution {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long institutionId;
    private String institutionName;

    @JsonIgnore
    @ManyToMany(mappedBy = "institutions")
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "workPlaces")
    private Set<Representative> representatives = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "institution")
    private Set<Place> places = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "institution")
    private Set<MonthTimetable> monthTimetables = new HashSet<>();

    public Institution() {
    }

    public Institution(String institutionName) {
        this.institutionName = institutionName;
    }

    public long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(long institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Representative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(Set<Representative> representatives) {
        this.representatives = representatives;
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    public Set<MonthTimetable> getMonthTimetables() {
        return monthTimetables;
    }

    public void setMonthTimetables(Set<MonthTimetable> monthTimetables) {
        this.monthTimetables = monthTimetables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Institution)) return false;
        Institution that = (Institution) o;
        return getInstitutionId() == that.getInstitutionId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstitutionId());
    }
}
