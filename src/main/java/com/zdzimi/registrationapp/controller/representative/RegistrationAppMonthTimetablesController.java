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

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/registration-app/{institutionName}/representatives/{representativeName}/timetables")
@CrossOrigin
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

    @GetMapping//ok
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
    public Set<Template> getNextTemplate(@PathVariable String institutionName,
                                         @PathVariable String representativeName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        MonthTimetable monthTimetable = monthTimetableService
                .findLastByRepresentativeAndInstitution(representative, institution);
        YearAndMonth yearAndMonth = yearAndMonthService.getYearAndMonth(monthTimetable);
        Template template = templateService.getNextTemplate(yearAndMonth);
        representativeLinkService.addLinksToNextTemplate(template, institutionName, representativeName);
        return Collections.singleton(template);
    }

    @PostMapping("/get-next-template")
    public MonthTimetableAndErrors setNextMonthTimetable(@PathVariable String institutionName,
                                                         @PathVariable String representativeName,
                                                         @RequestBody Template template) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        return monthTimetableAndErrorsService
                .createOrUpdate(institution, representative, template);
    }

    @GetMapping("/y/{year}")
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

    @GetMapping("/y/{year}/m/{month}")
    public Set<MonthTimetable> showMonthTimetablesByYearAndMonth(@PathVariable String institutionName,
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
        return Collections.singleton(monthTimetable);
    }

    @GetMapping("/y/{year}/m/{month}/get-template")
    public Set<Template> getMonthTemplate(@PathVariable String institutionName, @PathVariable String representativeName,
                                          @PathVariable int year, @PathVariable int month) {
        Template template = templateService.getMonthTemplate(year, month);
        representativeLinkService.addLinksToMonthTemplate(template, institutionName, representativeName, year, month);
        return Collections.singleton(template);
    }

    @PostMapping("/y/{year}/m/{month}/get-template")
    public MonthTimetableAndErrors updateMonthtimetable(@PathVariable String institutionName,
                                                        @PathVariable String representativeName,
                                                        @PathVariable int year,
                                                        @PathVariable int month,
                                                        @RequestBody Template template) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        return monthTimetableAndErrorsService
                .createOrUpdate(institution, representative, template);
    }

    @GetMapping("/y/{year}/m/{month}/d/{day}")
    public Set<DayTimetable> showDayTimetable(@PathVariable String institutionName,
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
        return Collections.singleton(dayTimetable);
    }

    @GetMapping("/y/{year}/m/{month}/d/{day}/get-template")
    public Set<Template> getDayTemplate(@PathVariable String institutionName, @PathVariable String representativeName,
                                        @PathVariable int year, @PathVariable int month, @PathVariable int day) {
        Template template = templateService.getDayTemplate(year, month, day);
        representativeLinkService.addLinksToDayTemplate(template, institutionName, representativeName, year, month, day);
        return Collections.singleton(template);
    }

    @PostMapping("/y/{year}/m/{month}/d/{day}/get-template")
    public MonthTimetableAndErrors createDayTimetable(@PathVariable String institutionName,
                                                      @PathVariable String representativeName,
                                                      @RequestBody Template template) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService
                .findByWorkPlacesAndUsername(institution, representativeName);
        return monthTimetableAndErrorsService.createOrUpdate(institution, representative, template);
    }

    @GetMapping("/y/{year}/m/{month}/d/{day}/v/{visitId}")
    public Set<Visit> showVisit(@PathVariable String institutionName, @PathVariable String representativeName,
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
        return Collections.singleton(visit);
    }

    @DeleteMapping("/y/{year}/m/{month}/d/{day}/v/{visitId}")
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
