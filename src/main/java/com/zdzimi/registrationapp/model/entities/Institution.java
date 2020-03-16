package com.zdzimi.registrationapp.model.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long institutionId;
    private String institutionName;

    @ManyToMany(mappedBy = "institutions")
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "workPlaces")
    private Set<Representative> representatives = new HashSet<>();

    @OneToMany(mappedBy = "institution")
    private Set<Place> places = new HashSet<>();

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
