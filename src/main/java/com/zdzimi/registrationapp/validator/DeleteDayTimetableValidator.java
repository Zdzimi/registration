package com.zdzimi.registrationapp.validator;

import com.zdzimi.registrationapp.model.entities.DayTimetable;

public class DeleteDayTimetableValidator implements RegistrationAppValidator {

    private DayTimetable dayTimetable;

    public DeleteDayTimetableValidator(DayTimetable dayTimetable) {
        this.dayTimetable = dayTimetable;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
