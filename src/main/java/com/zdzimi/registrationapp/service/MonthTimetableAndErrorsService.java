package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.model.DayTimetableAndErrors;
import com.zdzimi.registrationapp.model.MonthTimetableAndErrors;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.model.template.Day;
import com.zdzimi.registrationapp.model.template.Template;
import com.zdzimi.registrationapp.service.entities.MonthTimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthTimetableAndErrorsService {

    private MonthTimetableService monthTimetableService;
    private DayTimetableAndErrorsService dayTimetableAndErrorsService;

    @Autowired
    public MonthTimetableAndErrorsService(MonthTimetableService monthTimetableService,
                                          DayTimetableAndErrorsService dayTimetableAndErrorsService) {
        this.monthTimetableService = monthTimetableService;
        this.dayTimetableAndErrorsService = dayTimetableAndErrorsService;
    }

    public MonthTimetableAndErrors createOrUpdate(Institution institution, Representative representative, Template template) {

        int year = template.getYearAndMonth().getYear();
        int month = template.getYearAndMonth().getMonth();
        MonthTimetable monthTimetable = monthTimetableService
                .getOrCreate(year, month, representative, institution);

        MonthTimetableAndErrors monthTimetableAndErrors = new MonthTimetableAndErrors(year, month);
        List<Day> days = template.getDays();
        long visitTime = template.getVisitTime();

        if (visitTime > 0) {
            for (Day day : days) {
                DayTimetableAndErrors dayTimetableAndErrors = dayTimetableAndErrorsService
                        .createOrUpdate(day, monthTimetable, visitTime);
                monthTimetableAndErrors.getDayTimetableAndErrors().add(dayTimetableAndErrors);
            }
        }
        return monthTimetableAndErrors;
    }
}
