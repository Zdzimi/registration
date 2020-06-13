package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.service.UserLinkService;
import com.zdzimi.registrationapp.service.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/registration/{username}/institutions/{institutionName}/representatives/{representativeName}/timetables")
@CrossOrigin
public class RegistrationMonthTimetable {

    private UserService userService;
    private InstitutionService institutionService;
    private RepresentativeService representativeService;
    private MonthTimetableService monthTimetableService;
    private DayTimetableService dayTimetableService;
    private VisitService visitService;
    private UserLinkService userLinkService;

    @Autowired
    public RegistrationMonthTimetable(UserService userService,
                                      InstitutionService institutionService,
                                      RepresentativeService representativeService,
                                      MonthTimetableService monthTimetableService,
                                      DayTimetableService dayTimetableService,
                                      VisitService visitService,
                                      UserLinkService userLinkService) {
        this.userService = userService;
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.monthTimetableService = monthTimetableService;
        this.dayTimetableService = dayTimetableService;
        this.visitService = visitService;
        this.userLinkService = userLinkService;
    }

    @GetMapping
    public List<MonthTimetable> showActualTimetables(@PathVariable String username,
                                                     @PathVariable String institutionName,
                                                     @PathVariable String representativeName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        List<MonthTimetable> monthTimetableList = monthTimetableService
                .findActualByRepresentativeAndInstitution(representative, institution);
        userLinkService.addLinksToMonthTimetables(monthTimetableList, username, institutionName, representativeName);
        return monthTimetableList;
    }

    @GetMapping("/{yearMonth}")
    public Collection<MonthTimetable> showActualTimetable(@PathVariable String username, @PathVariable String institutionName,
                                              @PathVariable String representativeName, @PathVariable String yearMonth) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findActualByRepresentativeInstitutionAndYearMonth(representative, institution, yearMonth);
        userLinkService.addLinksToMonthTimetable(monthTimetable, username, institutionName, representativeName, yearMonth);
        return Collections.singleton(monthTimetable);
    }

    @GetMapping("/{yearMonth}/{day}")
    public Collection<DayTimetable> showActualDayTimetable(@PathVariable String username, @PathVariable String institutionName,
                                               @PathVariable String representativeName, @PathVariable String yearMonth,
                                               @PathVariable int day) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findActualByRepresentativeInstitutionAndYearMonth(representative, institution, yearMonth);
        DayTimetable dayTimetable = dayTimetableService.findByMonthTimetableAndDayOfMonth(monthTimetable, day);
        userLinkService.addLinksToDayTimetable(dayTimetable, username, institutionName, representativeName, yearMonth, day);
        return Collections.singleton(dayTimetable);
    }

    @GetMapping("/{yearMonth}/{day}/{visitId}")
    public Set<Visit> showActualVisit(@PathVariable String username, @PathVariable String institutionName,
                                      @PathVariable String representativeName, @PathVariable String yearMonth,
                                      @PathVariable int day, @PathVariable long visitId) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findActualByRepresentativeInstitutionAndYearMonth(representative, institution, yearMonth);
        DayTimetable dayTimetable = dayTimetableService.findByMonthTimetableAndDayOfMonth(monthTimetable, day);
        Visit visit = visitService.findByDayTimetableAndId(dayTimetable, visitId);
        userLinkService.addLinksToVisit(visit, username, institutionName, representativeName, yearMonth, day, visitId);
        return Collections.singleton(visit);
    }

    @GetMapping("/{yearMonth}/{day}/{visitId}/book")
    public Visit bookVisit(@PathVariable String username,
                           @PathVariable String institutionName,
                           @PathVariable String representativeName,
                           @PathVariable String yearMonth,
                           @PathVariable int day,
                           @PathVariable long visitId) {
        User user = userService.findUserByUsername(username);
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findActualByRepresentativeInstitutionAndYearMonth(representative, institution, yearMonth);
        DayTimetable dayTimetable = dayTimetableService.findByMonthTimetableAndDayOfMonth(monthTimetable, day);
        Visit visit = visitService.findByDayTimetableAndId(dayTimetable, visitId);
        return visitService.bookVisit(visit, user, institution);
    }
}
