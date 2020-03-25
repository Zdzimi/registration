package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.model.DayTimetableAndErrors;
import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.model.template.Day;
import com.zdzimi.registrationapp.service.entities.DayTimetableService;
import com.zdzimi.registrationapp.service.entities.PlaceService;
import com.zdzimi.registrationapp.service.entities.VisitService;
import com.zdzimi.registrationapp.validator.BookPlaceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DayTimetableAndErrorsService {

    private DayTimetableService dayTimetableService;
    private PlaceService placeService;
    private VisitService visitService;

    @Autowired
    public DayTimetableAndErrorsService(DayTimetableService dayTimetableService,
                                        PlaceService placeService,
                                        VisitService visitService) {
        this.dayTimetableService = dayTimetableService;
        this.placeService = placeService;
        this.visitService = visitService;
    }

    public DayTimetableAndErrors createAndSaveDayTimetable(Day day, MonthTimetable monthTimetable,
                                                           long visitTime, Institution institution) {
        DayTimetable dayTimetable = new DayTimetable(day.getDayNumber(), monthTimetable);
        dayTimetableService.save(dayTimetable);

        String placeName = day.getPlaceName();
        Place place = placeService.findByInstitutionAndPlaceName(institution, placeName);

        LocalTime timeWorkStart = day.getTimeStart();
        LocalTime timeWorkEnd = day.getTimeEnd();

        DayTimetableAndErrors dayTimetableAndErrors = new DayTimetableAndErrors(day.getDayNumber());

        int year = dayTimetable.getMonthTimetable().getYear();
        int month = dayTimetable.getMonthTimetable().getMonth();
        int dayOfMonth = dayTimetable.getDayOfMonth();
        List<Visit> visits = visitService.findAllInPlaceByYearMonthAndDay(place, year, month, dayOfMonth);   // ?? not too much??

        while (timeWorkStart.isBefore(timeWorkEnd)) {
            BookPlaceValidator bookPlaceValidator = new BookPlaceValidator(visits, timeWorkStart, visitTime);
            if (bookPlaceValidator.isValid()){
                Visit visit = new Visit(timeWorkStart, visitTime, dayTimetable, place);
                visitService.save(visit);
                dayTimetableAndErrors.getVisits().add(visit);
            }else {
                dayTimetableAndErrors.getErrors().add("Place " + placeName + " is booked "
                        + dayOfMonth + " - " + timeWorkStart + " - " + timeWorkStart.plusMinutes(visitTime));
            }
            timeWorkStart = timeWorkStart.plusMinutes(visitTime);
        }
        return dayTimetableAndErrors;
    }
}
