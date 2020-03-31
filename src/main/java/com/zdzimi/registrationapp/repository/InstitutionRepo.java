package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstitutionRepo extends JpaRepository<Institution, Long> {

    Optional<Institution> findByInstitutionName(String institutionName);

    List<Institution> findByUsers(User user);
}
