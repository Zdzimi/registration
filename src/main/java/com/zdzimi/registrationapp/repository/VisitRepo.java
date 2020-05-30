package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.model.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Long> {

    List<Visit> findByUser(User user);

    List<Visit> findByDayTimetable(DayTimetable dayTimetable);

    Optional<Visit> findByUserAndVisitId(User user, long visitId);

    Optional<Visit> findByDayTimetableAndVisitId(DayTimetable dayTimetable, long visitId);

    List<Visit> findByDayTimetableAndUser(DayTimetable dayTimetable, User user);

}
