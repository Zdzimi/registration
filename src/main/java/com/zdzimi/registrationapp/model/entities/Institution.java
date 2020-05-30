package com.zdzimi.registrationapp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.EntityModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Institution extends EntityModel {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long institutionId;
    @NotNull
    private String institutionName;
    @NotNull
    private String province;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String gateNumber;
    private String premisesNumber;
    @NotNull
    private String typeOfService;
    @NotNull
    private String description;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public String getPremisesNumber() {
        return premisesNumber;
    }

    public void setPremisesNumber(String premisesNumber) {
        this.premisesNumber = premisesNumber;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
