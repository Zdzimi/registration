package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepresentativeRepo extends JpaRepository<Representative, Long> {

    List<Representative> findByWorkPlaces(Institution institution);

    Optional<Representative> findByWorkPlacesAndUsername(Institution institution, String username);
}
