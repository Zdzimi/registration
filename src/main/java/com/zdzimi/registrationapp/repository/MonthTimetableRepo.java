package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthTimetableRepo extends JpaRepository<MonthTimetable, Long> {

}
