package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.VisitNotFoundException;
import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.repository.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private VisitRepo visitRepo;

    @Autowired
    public VisitService(VisitRepo visitRepo) {
        this.visitRepo = visitRepo;
    }


    public List<Visit> findByUser(User user) {
        return visitRepo.findByUser(user);
    }

    public List<Visit> findByDayTimetable(DayTimetable dayTimetable) {
        return visitRepo.findByDayTimetable(dayTimetable);
    }

    public Visit findByUserAndId(User user, long visitId) {
        return visitRepo.findByUserAndVisitId(user, visitId)
                .orElseThrow(() -> new VisitNotFoundException(visitId));
    }

    public Visit findByDayTimetableAndId(DayTimetable dayTimetable, long visitId) {
        return visitRepo.findByDayTimetableAndVisitId(dayTimetable, visitId)
                .orElseThrow(() -> new VisitNotFoundException(visitId));
    }

    public Set<Visit> findAllInPlaceByYearMonthAndDay(Place place, int year, int month, int dayOfMonth) {
        return place.getVisits().stream()
                .filter(visit -> visit.getDayTimetable().getMonthTimetable().getYear() == year)
                .filter(visit -> visit.getDayTimetable().getMonthTimetable().getMonth() == month)
                .filter(visit -> visit.getDayTimetable().getDayOfMonth() == dayOfMonth)
                .collect(Collectors.toSet());
    }

    public Visit bookVisit(Visit visit, User user, Institution institution) {
        if (visit.getUser() == null) {
            visit.setUser(user);
            user.getInstitutions().add(institution);
            return visitRepo.save(visit);
        }
        return visit;
    }

    public void save(Visit visit) {
        visitRepo.save(visit);
    }

    public Visit cancelVisit(User user, long visitId) {
        Visit visit = findByUserAndId(user, visitId);
        int dayOfMonth = visit.getDayTimetable().getDayOfMonth();
        int month = visit.getDayTimetable().getMonthTimetable().getMonth();
        int year = visit.getDayTimetable().getMonthTimetable().getYear();
        LocalDate visitDate = LocalDate.of(year, month, dayOfMonth);
        LocalDate now = LocalDate.now();
        if (now.isBefore(visitDate)) {
            visit.setUser(null);
            return visitRepo.save(visit);
        }
        return null;
    }

    public void deleteByDayTimetableAndId(DayTimetable dayTimetable, long visitId) {
        Visit visit = findByDayTimetableAndId(dayTimetable, visitId);
        if (visit.getUser() == null) {
            delete(visit);
        }
    }

    private void delete(Visit visit) {
        visitRepo.delete(visit);
    }
}
