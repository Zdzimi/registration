package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {
}
