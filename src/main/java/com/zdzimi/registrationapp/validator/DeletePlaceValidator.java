package com.zdzimi.registrationapp.validator;

import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.model.entities.Visit;

import java.time.LocalDate;
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
                .filter(visit -> getFullDate(visit).isAfter(LocalDate.now()))
                .collect(Collectors.toList());
        return visitList.isEmpty();
    }

    private LocalDate getFullDate(Visit visit) {
        int dayOfMonth = visit.getDayTimetable().getDayOfMonth();
        int month = visit.getDayTimetable().getMonthTimetable().getMonth();
        int year = visit.getDayTimetable().getMonthTimetable().getYear();
        return LocalDate.of(year,month,dayOfMonth);
    }
}
