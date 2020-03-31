package com.zdzimi.registrationapp.controller.representative;

import com.zdzimi.registrationapp.model.MonthTimetableAndErrors;
import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.model.template.Template;
import com.zdzimi.registrationapp.model.template.YearAndMonth;
import com.zdzimi.registrationapp.service.MonthTimetableAndErrorsService;
import com.zdzimi.registrationapp.service.RepresentativeLinkService;
import com.zdzimi.registrationapp.service.entities.*;
import com.zdzimi.registrationapp.service.template.TemplateService;
import com.zdzimi.registrationapp.service.template.YearAndMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration-app/{institutionName}/representative/{representativeName}/timetables")
public class RegistrationAppMonthTimetablesController {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;
    private MonthTimetableService monthTimetableService;
    private DayTimetableService dayTimetableService;
    private VisitService visitService;
    private TemplateService templateService;
    private YearAndMonthService yearAndMonthService;
    private MonthTimetableAndErrorsService monthTimetableAndErrorsService;
    private RepresentativeLinkService representativeLinkService;

    @Autowired
    public RegistrationAppMonthTimetablesController(InstitutionService institutionService,
                                                    RepresentativeService representativeService,
                                                    MonthTimetableService monthTimetableService,
                                                    DayTimetableService dayTimetableService,
                                                    VisitService visitService,
                                                    TemplateService templateService,
                                                    YearAndMonthService yearAndMonthService,
                                                    MonthTimetableAndErrorsService monthTimetableAndErrorsService,
                                                    RepresentativeLinkService representativeLinkService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.monthTimetableService = monthTimetableService;
        this.dayTimetableService = dayTimetableService;
        this.visitService = visitService;
        this.templateService = templateService;
        this.yearAndMonthService = yearAndMonthService;
        this.monthTimetableAndErrorsService = monthTimetableAndErrorsService;
        this.representativeLinkService = representativeLinkService;
    }

    @GetMapping
    public List<MonthTimetable> showMonthTimetables(@PathVariable String institutionName,
                                                                    @PathVariable String representativeName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        List<MonthTimetable> monthTimetables = monthTimetableService
                .findByRepresentativeAndInstitution(representative, institution);
        representativeLinkService.addLinksToAllMonthTimetables(monthTimetables, institutionName, representativeName);
        return monthTimetables;
    }

    @GetMapping("/get-next-template")
    public Template getNextTemplate(@PathVariable String institutionName,
                                    @PathVariable String representativeName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findLastByRepresentativeAndInstitution(representative, institution);
        YearAndMonth yearAndMonth = yearAndMonthService.getYearAndMonth(monthTimetable);
        Template template = templateService.getNextTemplate(yearAndMonth);
        representativeLinkService.addLinksToNextTemplate(template, institutionName, representativeName);
        return template;
    }

    @PostMapping("/get-next-template")
    public MonthTimetableAndErrors setNextMonthTimetable(@PathVariable String institutionName,
                                                         @PathVariable String representativeName,
                                                         @RequestBody Template template) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetableAndErrors timetableAndErrors = monthTimetableAndErrorsService
                .createOrUpdate(institution, representative, template);
        representativeLinkService
                .addLinksToNextTimetableAndErrors(timetableAndErrors, institutionName, representativeName);
        return timetableAndErrors;
    }

    @GetMapping("/{year}")
    public List<MonthTimetable> showMonthTimetablesByYear(@PathVariable String institutionName,
                                                          @PathVariable String representativeName,
                                                          @PathVariable int year) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        List<MonthTimetable> monthTimetables = monthTimetableService
                .findByRepresentativeAndInstitutionAndYear(representative, institution, year);
        representativeLinkService.addLinksToAllMonthTimetables(monthTimetables, institutionName, representativeName);
        return monthTimetables;
    }

    @GetMapping("/{year}/{month}")
    public MonthTimetable showMonthTimetablesByYearAndMonth(@PathVariable String institutionName,
                                                            @PathVariable String representativeName,
                                                            @PathVariable int year,
                                                            @PathVariable int month) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findByRepresentativeAndInstitutionAndYearAndMonth(representative, institution, year, month);
        representativeLinkService
                .addLinksToMonthTimetable(monthTimetable, institutionName, representativeName, year, month);
        return monthTimetable;
    }

    @GetMapping("/{year}/{month}/get-template")
    public Template getMonthTemplate(@PathVariable String institutionName, @PathVariable String representativeName,
                                     @PathVariable int year, @PathVariable int month) {
        Template template = templateService.getMonthTemplate(year, month);
        representativeLinkService.addLinksToMonthTemplate(template, institutionName, representativeName, year, month);
        return template;
    }

    @PostMapping("/{year}/{month}/get-template")
    public MonthTimetableAndErrors updateMonthtimetable(@PathVariable String institutionName,
                                                        @PathVariable String representativeName,
                                                        @PathVariable int year,
                                                        @PathVariable int month,
                                                        @RequestBody Template template) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetableAndErrors timetableAndErrors = monthTimetableAndErrorsService
                .createOrUpdate(institution, representative, template);
        representativeLinkService
                .addLinksToMonthTimetableAndErrors(template, institutionName, representativeName, year, month);
        return timetableAndErrors;
    }

    @GetMapping("/{year}/{month}/{day}")
    public DayTimetable showDayTimetable(@PathVariable String institutionName,
                                         @PathVariable String representativeName,
                                         @PathVariable int year,
                                         @PathVariable int month,
                                         @PathVariable int day) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findByRepresentativeAndInstitutionAndYearAndMonth(representative, institution, year, month);
        DayTimetable dayTimetable = dayTimetableService.findByMonthTimetableAndDayOfMonth(monthTimetable, day);
        representativeLinkService
                .addLinksToDayTimetable(dayTimetable, institutionName, representativeName, year, month, day);
        return dayTimetable;
    }

    @GetMapping("/{year}/{month}/{day}/get-template")
    public Template getDayTemplate(@PathVariable String institutionName, @PathVariable String representativeName,
                                   @PathVariable int year, @PathVariable int month, @PathVariable int day) {
        Template template = templateService.getDayTemplate(year, month, day);
        representativeLinkService.addLinksToDayTemplate(template, institutionName, representativeName, year, month, day);
        return template;
    }

    @PostMapping("/{year}/{month}/{day}/get-template")
    public MonthTimetableAndErrors createDayTimetable(@PathVariable String institutionName,
                                                      @PathVariable String representativeName,
                                                      @PathVariable int year,
                                                      @PathVariable int month,
                                                      @PathVariable int day,
                                                      @RequestBody Template template) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetableAndErrors timetableAndErrors = monthTimetableAndErrorsService
                .createOrUpdate(institution, representative, template);
        representativeLinkService
                .addLinksToDayTimetableAndErrors(timetableAndErrors, institutionName, representativeName, year, month, day);
        return timetableAndErrors;
    }

    @GetMapping("/{year}/{month}/{day}/{visitId}")
    public Visit showVisit(@PathVariable String institutionName, @PathVariable String representativeName,
                           @PathVariable int year, @PathVariable int month,
                           @PathVariable int day, @PathVariable long visitId) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findByRepresentativeAndInstitutionAndYearAndMonth(representative, institution, year, month);
        DayTimetable dayTimetable = dayTimetableService.findByMonthTimetableAndDayOfMonth(monthTimetable, day);
        Visit visit = visitService.findByDayTimetableAndId(dayTimetable, visitId);
        representativeLinkService.addBackLinks(visit, institutionName, representativeName, year, month, day);
        return visit;
    }

    @PostMapping("/{year}/{month}/{day}/{visitId}")
    public void deleteVisit(@PathVariable String institutionName, @PathVariable String representativeName,
                            @PathVariable int year, @PathVariable int month,
                            @PathVariable int day, @PathVariable long visitId) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findByRepresentativeAndInstitutionAndYearAndMonth(representative, institution, year, month);
        DayTimetable dayTimetable = dayTimetableService.findByMonthTimetableAndDayOfMonth(monthTimetable, day);
        visitService.deleteByDayTimetableAndId(dayTimetable, visitId);
    }
}
