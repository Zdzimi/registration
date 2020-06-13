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
import java.util.Optional;
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

        Optional<Place> place = placeService
                .getPlaceByInstitutionAndPlaceName(monthTimetable.getInstitution(), day.getPlaceName());

        DayTimetableAndErrors dayTimetableAndErrors = new DayTimetableAndErrors(day.getDayNumber());

        if (place.isPresent()) {
            LocalTime timeWorkStart = day.getTimeStart();
            LocalTime timeWorkEnd = day.getTimeEnd();

            Set<Visit> visitSetByPlaceAndDay = getVisitListFromPlaceAndDay(dayTimetable, place.get());
            Set<Visit> visitSetByRepresentativeAndDay = dayTimetable.getVisits();

            while (timeWorkStart.isBefore(timeWorkEnd)) {
                VisitValidator placeBookedValidator = new VisitValidator(visitSetByPlaceAndDay, timeWorkStart, visitTime);
                VisitValidator representativeValidator = new VisitValidator(visitSetByRepresentativeAndDay, timeWorkStart, visitTime);

                if (placeBookedValidator.isValid() && representativeValidator.isValid()){
                    Visit visit = new Visit(timeWorkStart, visitTime, dayTimetable, place.get());
                    visitService.save(visit);
                    dayTimetableAndErrors.getVisits().add(visit);
                }else {
                    if (!placeBookedValidator.isValid()) {
                        dayTimetableAndErrors.getErrors().add(String.format(
                                "Stamowisko %s jest już zarezerwowane %s.%s.%dr. - %s - %s",
                                day.getPlaceName(),
                                dayTimetable.getDayOfMonth() < 10
                                        ? String.format("0%d", dayTimetable.getDayOfMonth())
                                        : dayTimetable.getDayOfMonth(),
                                dayTimetable.getMonthTimetable().getMonth() < 10
                                        ? String.format("0%d", dayTimetable.getMonthTimetable().getMonth())
                                        : dayTimetable.getMonthTimetable().getMonth(),
                                dayTimetable.getMonthTimetable().getYear(),
                                timeWorkStart,
                                timeWorkStart.plusMinutes(visitTime)));
                    }
                    if (!representativeValidator.isValid()) {
                        dayTimetableAndErrors.getErrors().add(String.format(
                                        "%s - %s.%s.%dr. - masz już wizytę w tym czasie",
                                        timeWorkStart,
                                dayTimetable.getDayOfMonth() < 10
                                        ? String.format("0%d", dayTimetable.getDayOfMonth())
                                        : dayTimetable.getDayOfMonth(),
                                dayTimetable.getMonthTimetable().getMonth() < 10
                                        ? String.format("0%d", dayTimetable.getMonthTimetable().getMonth())
                                        : dayTimetable.getMonthTimetable().getMonth(),
                                        dayTimetable.getMonthTimetable().getYear()
                        ));
                    }
                }
                timeWorkStart = timeWorkStart.plusMinutes(visitTime);
            }
        } else {
            dayTimetableAndErrors.getErrors().add(String.format(
                    "%s.%s.%dr. - dzień wolny",
                    dayTimetable.getDayOfMonth() < 10
                            ? String.format("0%d", dayTimetable.getDayOfMonth())
                            : dayTimetable.getDayOfMonth(),
                    dayTimetable.getMonthTimetable().getMonth() < 10
                            ? String.format("0%d", dayTimetable.getMonthTimetable().getMonth())
                            : dayTimetable.getMonthTimetable().getMonth(),
                    dayTimetable.getMonthTimetable().getYear()
            ));
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
