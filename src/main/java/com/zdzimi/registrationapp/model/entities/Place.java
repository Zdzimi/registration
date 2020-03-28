package com.zdzimi.registrationapp.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Place {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long placeId;
    private String placeName;

    @JsonIgnore
    @ManyToOne
    private Institution institution;

    @JsonIgnore
    @OneToMany(mappedBy = "place")
    private Set<Visit> visits = new HashSet<>();

    public Place() {
    }

    public Place(String placeName, Institution institution) {
        this.placeName = placeName;
        this.institution = institution;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;
        Place place = (Place) o;
        return getPlaceId() == place.getPlaceId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceId());
    }
}
