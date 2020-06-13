package com.zdzimi.registrationapp.validator;

import com.zdzimi.registrationapp.model.entities.Visit;

import java.time.LocalDate;

public class DeleteOrBookVisitValidator implements RegistrationAppValidator {

    private Visit visit;

    public DeleteOrBookVisitValidator(Visit visit) {
        this.visit = visit;
    }

    @Override
    public boolean isValid() {
        LocalDate now = LocalDate.now();
        int dayOfMonth = visit.getDayTimetable().getDayOfMonth();
        int month = visit.getDayTimetable().getMonthTimetable().getMonth();
        int year = visit.getDayTimetable().getMonthTimetable().getYear();
        LocalDate visitDate = LocalDate.of(year, month, dayOfMonth);
        return now.isBefore(visitDate) && visit.getUser() == null;
    }
}
