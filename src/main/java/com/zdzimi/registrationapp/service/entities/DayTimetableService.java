package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.DayTimetableNotFoundException;
import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.repository.DayTimetableRepo;
import com.zdzimi.registrationapp.validator.DeleteDayTimetableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayTimetableService {

    private DayTimetableRepo dayTimetableRepo;

    @Autowired
    public DayTimetableService(DayTimetableRepo dayTimetableRepo) {
        this.dayTimetableRepo = dayTimetableRepo;
    }

    public DayTimetable findByMonthTimetableAndDayOfMonth(MonthTimetable monthTimetable, int day) {
        return dayTimetableRepo.findByMonthTimetableAndDayOfMonth(monthTimetable, day)
                .orElseThrow(() -> new DayTimetableNotFoundException(monthTimetable.getYear(), monthTimetable.getMonth(), day));
    }

    public void save(DayTimetable dayTimetable) {
        dayTimetableRepo.save(dayTimetable);
    }

    public void delete(MonthTimetable monthTimetable, int day) {
        DayTimetable dayTimetable = dayTimetableRepo.findByMonthTimetableAndDayOfMonth(monthTimetable, day)
                .orElseThrow(() -> new DayTimetableNotFoundException(monthTimetable.getYear(), monthTimetable.getMonth(), day));

        DeleteDayTimetableValidator deleteDayTimetableValidator = new DeleteDayTimetableValidator(dayTimetable);
        if (deleteDayTimetableValidator.isValid()) {
            dayTimetableRepo.delete(dayTimetable);      //  todo
        }
    }
}
