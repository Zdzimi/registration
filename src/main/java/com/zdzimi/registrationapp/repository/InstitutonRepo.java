package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutonRepo extends JpaRepository<Institution, Long> {

    Optional<Institution> findByInstitutionName(String institutionName);
}
