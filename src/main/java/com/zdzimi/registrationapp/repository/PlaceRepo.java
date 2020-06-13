package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {

    List<Place> findByInstitution(Institution institution);

    Optional<Place> findByInstitutionAndPlaceName(Institution institution, String placeName);

    Optional<Place> findByInstitutionAndPlaceId(Institution institution, long placeId);
}
