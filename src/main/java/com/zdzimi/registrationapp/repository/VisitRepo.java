package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Long> {
}
