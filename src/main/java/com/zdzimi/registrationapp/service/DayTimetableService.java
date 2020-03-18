package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.exception.DayTimetableNotFoundException;
import com.zdzimi.registrationapp.model.MonthTimetableAndErrors;
import com.zdzimi.registrationapp.model.template.Day;
import com.zdzimi.registrationapp.model.template.Template;
import com.zdzimi.registrationapp.model.DayTimetableAndErrors;
import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.repository.DayTimetableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayTimetableService {

    private DayTimetableRepo dayTimetableRepo;
    private VisitService visitService;

    @Autowired
    public DayTimetableService(DayTimetableRepo dayTimetableRepo, VisitService visitService) {
        this.dayTimetableRepo = dayTimetableRepo;
        this.visitService = visitService;
    }

    public MonthTimetableAndErrors createAndSaveDayTimetables(MonthTimetable monthTimetable, Template template, Institution institution) {
        List<Day> days = template.getDays();
        long visitTime = template.getVisitTime();

        MonthTimetableAndErrors monthTimetableAndErrors = new MonthTimetableAndErrors(monthTimetable.getYear(), monthTimetable.getMonth());

        for (Day day : days) {
            DayTimetable dayTimetable = new DayTimetable(day.getDayNumber(), monthTimetable);
            save(dayTimetable);
            DayTimetableAndErrors dayTimetableAndErrors = visitService.createAndSaveVisits(dayTimetable, day, visitTime, institution);
            monthTimetableAndErrors.getDayTimetableAndErrors().add(dayTimetableAndErrors);
        }
        return monthTimetableAndErrors;
    }

    private void save(DayTimetable dayTimetable) {
        dayTimetableRepo.save(dayTimetable);
    }

    public DayTimetable findDayTimetable(MonthTimetable monthTimetable, int day) {
        return monthTimetable.getDayTimetableSet().stream()
                .filter(dayTimetable -> dayTimetable.getDayOfMonth() == day)
                .findFirst()
                .orElseThrow(() -> new DayTimetableNotFoundException(monthTimetable.getYear(), monthTimetable.getMonth(), day));
    }
}
