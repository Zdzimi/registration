package com.zdzimi.registrationapp.controller.representative;

import com.zdzimi.registrationapp.model.MonthTimetableAndErrors;
import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.model.template.Template;
import com.zdzimi.registrationapp.model.template.YearAndMonth;
import com.zdzimi.registrationapp.service.MonthTimetableAndErrorsService;
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

    @Autowired
    public RegistrationAppMonthTimetablesController(InstitutionService institutionService,
                                                    RepresentativeService representativeService,
                                                    MonthTimetableService monthTimetableService,
                                                    DayTimetableService dayTimetableService,
                                                    VisitService visitService,
                                                    TemplateService templateService,
                                                    YearAndMonthService yearAndMonthService,
                                                    MonthTimetableAndErrorsService monthTimetableAndErrorsService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.monthTimetableService = monthTimetableService;
        this.dayTimetableService = dayTimetableService;
        this.visitService = visitService;
        this.templateService = templateService;
        this.yearAndMonthService = yearAndMonthService;
        this.monthTimetableAndErrorsService = monthTimetableAndErrorsService;
    }

    @GetMapping
    public List<MonthTimetable> showMonthTimetables(@PathVariable String institutionName,
                                                    @PathVariable String representativeName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        return monthTimetableService.findByRepresentativeAndInstitution(representative, institution);
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
        return templateService.getNextTemplate(yearAndMonth);
    }

    @PostMapping("/get-next-template")
    public MonthTimetableAndErrors setNextMonthTimetable(@PathVariable String institutionName,
                                                         @PathVariable String representativeName,
                                                         @RequestBody Template template) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        return monthTimetableAndErrorsService.createOrUpdate(institution, representative, template);
    }

    @GetMapping("/{year}")
    public List<MonthTimetable> showMonthTimetablesByYear(@PathVariable String institutionName,
                                                          @PathVariable String representativeName,
                                                          @PathVariable int year) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        return monthTimetableService.findByRepresentativeAndInstitutionAndYear(representative, institution, year);
    }

    @GetMapping("/{year}/{month}")
    public MonthTimetable showMonthTimetablesByYearAndMonth(@PathVariable String institutionName,
                                                            @PathVariable String representativeName,
                                                            @PathVariable int year,
                                                            @PathVariable int month) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        return monthTimetableService
                .findByRepresentativeAndInstitutionAndYearAndMonth(representative, institution, year, month);
    }

    @GetMapping("/{year}/{month}/get-template")
    public Template getMonthTemplate(@PathVariable int year, @PathVariable int month) {
        return templateService.getMonthTemplate(year, month);
    }

    @PostMapping("/{year}/{month}/get-template")
    public MonthTimetableAndErrors updateMonthtimetable(@PathVariable String institutionName,
                                                        @PathVariable String representativeName,
                                                        @RequestBody Template template) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService.findByWorkPlacesAndUsername(institution, representativeName);
        return monthTimetableAndErrorsService.createOrUpdate(institution, representative, template);
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
        return dayTimetableService.findByMonthTimetableAndDayOfMonth(monthTimetable, day);
    }

    @GetMapping("/{year}/{month}/{day}/get-template")
    public Template getDayTemplate(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
        return templateService.getDayTemplate(year, month, day);
    }

    @PostMapping("/{year}/{month}/{day}/get-template")
    public MonthTimetableAndErrors createDayTimetable(@PathVariable String institutionName,
                                                      @PathVariable String representativeName,
                                                      @RequestBody Template template) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService.findByWorkPlacesAndUsername(institution, representativeName);
        return monthTimetableAndErrorsService.createOrUpdate(institution, representative, template);
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
        return visitService.findByDayTimetableAndId(dayTimetable, visitId);
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
