package com.zdzimi.registrationapp.validator;

import com.zdzimi.registrationapp.model.entities.MonthTimetable;

public class DeleteMonthTimetableValidator implements RegistrationAppValidator {

    private MonthTimetable monthTimetable;

    public DeleteMonthTimetableValidator(MonthTimetable monthTimetable) {
        this.monthTimetable = monthTimetable;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
