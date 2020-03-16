package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.DayTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayTimetableRepo extends JpaRepository<DayTimetable, Long> {
}
