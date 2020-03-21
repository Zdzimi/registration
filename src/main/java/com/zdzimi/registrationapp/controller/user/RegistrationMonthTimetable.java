package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institutions/{institutionName}/representatives/{representativeName}/timetables")
public class RegistrationMonthTimetable {

    private UserService userService;
    private InstitutionService institutionService;
    private RepresentativeService representativeService;
    private MonthTimetableService monthTimetableService;
    private DayTimetableService dayTimetableService;
    private VisitService visitService;

    @Autowired
    public RegistrationMonthTimetable(UserService userService,
                                      InstitutionService institutionService,
                                      RepresentativeService representativeService,
                                      MonthTimetableService monthTimetableService,
                                      DayTimetableService dayTimetableService,
                                      VisitService visitService) {
        this.userService = userService;
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.monthTimetableService = monthTimetableService;
        this.dayTimetableService = dayTimetableService;
        this.visitService = visitService;
    }

    @GetMapping
    public List<MonthTimetable> showActualTimetables(@PathVariable String institutionName,
                                                     @PathVariable String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        return monthTimetableService.findActualByRepresentativeAndInstitution(representative, institution);
    }

    @GetMapping("/{yearMonth}")
    public MonthTimetable showActualTimetable(@PathVariable String institutionName,
                                              @PathVariable String representativeName,
                                              @PathVariable String yearMonth) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        return monthTimetableService.findActualByRepresentativeInstitutionAndId(representative, institution, yearMonth);
    }

    @GetMapping("/{yearMonth}/{day}")
    public DayTimetable showActualDayTimetable(@PathVariable String institutionName,
                                               @PathVariable String representativeName,
                                               @PathVariable String yearMonth,
                                               @PathVariable int day) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findActualByRepresentativeInstitutionAndId(representative, institution, yearMonth);
        return dayTimetableService.findDayTimetable(monthTimetable, day);
    }

    @GetMapping("/{yearMonth}/{day}/{visitId}")
    public Visit showActualVisit(@PathVariable String institutionName,
                                 @PathVariable String representativeName,
                                 @PathVariable String yearMonth,
                                 @PathVariable int day,
                                 @PathVariable long visitId) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findActualByRepresentativeInstitutionAndId(representative, institution, yearMonth);
        DayTimetable dayTimetable = dayTimetableService.findDayTimetable(monthTimetable, day);
        return visitService.findByDayTimetableAndId(dayTimetable, visitId);
    }

    @GetMapping("/{yearMonth}/{day}/{visitId}/book")
    public Visit bookVisit(@PathVariable String username,
                           @PathVariable String institutionName,
                           @PathVariable String representativeName,
                           @PathVariable String yearMonth,
                           @PathVariable int day,
                           @PathVariable long visitId) {
        User user = userService.findUserByUsername(username);
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findActualByRepresentativeInstitutionAndId(representative, institution, yearMonth);
        DayTimetable dayTimetable = dayTimetableService.findDayTimetable(monthTimetable, day);
        Visit visit = visitService.findByDayTimetableAndId(dayTimetable, visitId);
        return visitService.bookVisit(visit, user);
    }
}
