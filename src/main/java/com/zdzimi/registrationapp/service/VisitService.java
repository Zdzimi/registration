package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.model.template.Day;
import com.zdzimi.registrationapp.model.DayTimetableAndErrors;
import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.model.entities.Visit;
import com.zdzimi.registrationapp.repository.VisitRepo;
import com.zdzimi.registrationapp.validator.BookPlaceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private VisitRepo visitRepo;
    private PlaceService placeService;

    @Autowired
    public VisitService(VisitRepo visitRepo, PlaceService placeService) {
        this.visitRepo = visitRepo;
        this.placeService = placeService;
    }


    public DayTimetableAndErrors createAndSaveVisits(DayTimetable dayTimetable, Day day, long visitTime, Institution institution) {
        String placeName = day.getPlaceName();
        Place place = placeService.findPlace(institution, placeName);

        LocalTime timeWorkStart = day.getTimeStart();
        LocalTime timeWorkEnd = day.getTimeEnd();

        DayTimetableAndErrors dayTimetableAndErrors = new DayTimetableAndErrors(day.getDayNumber());

        int year = dayTimetable.getMonthTimetable().getYear();
        int month = dayTimetable.getMonthTimetable().getMonth();
        int dayOfMonth = dayTimetable.getDayOfMonth();
        List<Visit> visits = findAllInPlaceByYearMonthAndDay(place, year, month, dayOfMonth);   // ?? not too much??

        while (timeWorkStart.isBefore(timeWorkEnd)) {
            BookPlaceValidator bookPlaceValidator = new BookPlaceValidator(visits, timeWorkStart, visitTime);
            if (bookPlaceValidator.isValid()){
                Visit visit = new Visit(timeWorkStart, visitTime, dayTimetable, place);
                save(visit);
                dayTimetableAndErrors.getVisits().add(visit);
            }else {
                dayTimetableAndErrors.getErrors().add("Place " + placeName + " is booked " + dayOfMonth + " - " + timeWorkStart + " - " + timeWorkStart.plusMinutes(visitTime));
            }
            timeWorkStart = timeWorkStart.plusMinutes(visitTime);
        }
        return dayTimetableAndErrors;
    }

    private List<Visit> findAllInPlaceByYearMonthAndDay(Place place, int year, int month, int dayOfMonth) {
        return place.getVisits().stream()
                .filter(visit -> visit.getDayTimetable().getMonthTimetable().getYear() == year)
                .filter(visit -> visit.getDayTimetable().getMonthTimetable().getMonth() == month)
                .filter(visit -> visit.getDayTimetable().getDayOfMonth() == dayOfMonth)
                .collect(Collectors.toList());
    }

    private void save(Visit visit) {
        visitRepo.save(visit);
    }
}
