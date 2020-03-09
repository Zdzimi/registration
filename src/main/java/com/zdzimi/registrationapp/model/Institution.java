package com.zdzimi.registrationapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long institutionId;
    private String institutionName;

    @JsonIgnore
    @OneToMany(mappedBy = "institution")
    private Set<User> users;

    @OneToMany(mappedBy = "institution")
    private Set<Place> places;

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

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }
}
