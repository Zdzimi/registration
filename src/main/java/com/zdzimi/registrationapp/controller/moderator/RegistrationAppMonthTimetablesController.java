package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.MonthTimetableAndErrors;
import com.zdzimi.registrationapp.model.template.Template;
import com.zdzimi.registrationapp.model.template.YearAndMonth;
import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.service.*;
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
    private TemplateService templateService;

    @Autowired
    public RegistrationAppMonthTimetablesController(InstitutionService institutionService,
                                                    RepresentativeService representativeService,
                                                    MonthTimetableService monthTimetableService,
                                                    DayTimetableService dayTimetableService,
                                                    TemplateService templateService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.monthTimetableService = monthTimetableService;
        this.dayTimetableService = dayTimetableService;
        this.templateService = templateService;
    }

    @GetMapping
    public List<MonthTimetable> showMonthTimetables(@PathVariable String institutionName,
                                                    @PathVariable String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        return monthTimetableService.findAllByRepresentativeAndInstitution(representative, institution);
    }

    @GetMapping("/get-next-template")
    public Template getNextTemplate(@PathVariable String institutionName,
                                    @PathVariable String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        YearAndMonth yearAndMonth = monthTimetableService
                .findLastMonthTimetableByRepresentativeAneInstitution(representative, institution);
        return templateService.getNextEmptyTemloate(yearAndMonth);
    }

    @PostMapping("/get-next-template")
    public MonthTimetableAndErrors setNextMonthTimetable(@PathVariable String institutionName,
                                                         @PathVariable String representativeName,
                                                         @RequestBody Template template) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        return monthTimetableService.createAndSaveMonthTimetableFromTemplate(institution,
                representative,template);
    }

    @GetMapping("/{year}")
    public List<MonthTimetable> showMonthTimetablesByYear(@PathVariable String institutionName,
                                                          @PathVariable String representativeName,
                                                          @PathVariable int year) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        return monthTimetableService.findByRepresentativeInstitutionAndYear(representative, institution, year);
    }

    @GetMapping("/{year}/{month}")
    public MonthTimetable showMonthTimetablesByYearAndMonth(@PathVariable String institutionName,
                                                            @PathVariable String representativeName,
                                                            @PathVariable int year,
                                                            @PathVariable int month) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        return monthTimetableService
                .findByRepresentativeInstitutionYearAndMonth(representative, institution, year, month);
    }

    @GetMapping("/{year}/{month}/{day}")
    public DayTimetable showDayTimetable(@PathVariable String institutionName,
                                         @PathVariable String representativeName,
                                         @PathVariable int year,
                                         @PathVariable int month,
                                         @PathVariable int day) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findByRepresentativeInstitutionYearAndMonth(representative, institution, year, month);
        return dayTimetableService.findDayTimetable(monthTimetable, day);
    }
}
