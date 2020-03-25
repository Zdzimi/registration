package com.zdzimi.registrationapp.repository;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.entities.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthTimetableRepo extends JpaRepository<MonthTimetable, Long> {

    List<MonthTimetable> findByRepresentativeAndInstitution(Representative representative, Institution institution);

    List<MonthTimetable> findByRepresentativeAndInstitutionAndYear(Representative representative,
                                                                   Institution institution,
                                                                   int year);

    Optional<MonthTimetable> findByRepresentativeAndInstitutionAndYearAndMonth(Representative representative,
                                                                               Institution institution,
                                                                               int year,
                                                                               int month);
}
