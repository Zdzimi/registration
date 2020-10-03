package com.zdzimi.registrationapp.validator;

import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.model.entities.Visit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DeletePlaceValidator implements RegistrationAppValidator {

    private Place place;

    public DeletePlaceValidator(Place place) {
        this.place = place;
    }

    @Override
    public boolean isValid() {
        List<Visit> visitList = place.getVisits().stream()
                .filter(visit -> !getFullDateTime(visit).isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        return visitList.isEmpty();
    }

    private LocalDateTime getFullDateTime(Visit visit) {
        int hour = visit.getVisitTimeStart().getHour();
        int minute = visit.getVisitTimeStart().getMinute();
        int dayOfMonth = visit.getDayTimetable().getDayOfMonth();
        int month = visit.getDayTimetable().getMonthTimetable().getMonth();
        int year = visit.getDayTimetable().getMonthTimetable().getYear();
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }
}
