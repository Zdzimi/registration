package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentativeRepo extends JpaRepository<Representative, Long> {
}
