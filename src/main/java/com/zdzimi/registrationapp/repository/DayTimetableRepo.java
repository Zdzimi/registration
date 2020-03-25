package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DayTimetableRepo extends JpaRepository<DayTimetable, Long> {

    Optional<DayTimetable> findByMonthTimetableAndDayOfMonth(MonthTimetable monthTimetable, int day);
}
