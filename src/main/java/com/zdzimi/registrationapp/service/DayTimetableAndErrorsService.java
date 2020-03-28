package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.model.DayTimetableAndErrors;
import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.model.template.Day;
import com.zdzimi.registrationapp.service.entities.DayTimetableService;
import com.zdzimi.registrationapp.service.entities.PlaceService;
import com.zdzimi.registrationapp.service.entities.VisitService;
import com.zdzimi.registrationapp.validator.VisitValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Set;

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

    public DayTimetableAndErrors createOrUpdate(Day day, MonthTimetable monthTimetable, long visitTime) {
        DayTimetable dayTimetable = dayTimetableService.getOrCreate(day.getDayNumber(), monthTimetable);

        Place place = placeService.findByInstitutionAndPlaceName(monthTimetable.getInstitution(), day.getPlaceName());

        LocalTime timeWorkStart = day.getTimeStart();
        LocalTime timeWorkEnd = day.getTimeEnd();

        DayTimetableAndErrors dayTimetableAndErrors = new DayTimetableAndErrors(day.getDayNumber());

        Set<Visit> visitSetByPlaceAndDay = getVisitListFromPlaceAndDay(dayTimetable, place);
        Set<Visit> visitSetByRepresentativeAndDay = dayTimetable.getVisits();

        while (timeWorkStart.isBefore(timeWorkEnd)) {
            VisitValidator placeBookedValidator = new VisitValidator(visitSetByPlaceAndDay, timeWorkStart, visitTime);
            VisitValidator representativeValidator = new VisitValidator(visitSetByRepresentativeAndDay, timeWorkStart, visitTime);

            if (placeBookedValidator.isValid() && representativeValidator.isValid()){
                Visit visit = new Visit(timeWorkStart, visitTime, dayTimetable, place);
                visitService.save(visit);
                dayTimetableAndErrors.getVisits().add(visit);
            }else {
                if (!placeBookedValidator.isValid()) {
                    dayTimetableAndErrors.getErrors().add("Place " + day.getPlaceName() + " is booked "
                            + day.getDayNumber() + " - " + timeWorkStart + " - " + timeWorkStart.plusMinutes(visitTime));
                }
                if (!representativeValidator.isValid()) {
                    dayTimetableAndErrors.getErrors().add("You can't be in two places in the same time...");
                }
            }
            timeWorkStart = timeWorkStart.plusMinutes(visitTime);
        }
        return dayTimetableAndErrors;
    }

    private Set<Visit> getVisitListFromPlaceAndDay(DayTimetable dayTimetable, Place place) {
        int year = dayTimetable.getMonthTimetable().getYear();
        int month = dayTimetable.getMonthTimetable().getMonth();
        int dayOfMonth = dayTimetable.getDayOfMonth();
        return visitService.findAllInPlaceByYearMonthAndDay(place, year, month, dayOfMonth);
    }
}
