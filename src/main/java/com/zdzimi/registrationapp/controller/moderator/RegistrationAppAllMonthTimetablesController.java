package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.Template;
import com.zdzimi.registrationapp.model.YearAndMonth;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.MonthTimetableService;
import com.zdzimi.registrationapp.service.RepresentativeService;
import com.zdzimi.registrationapp.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration-app/{institutionName}/representative/{representativeName}/timetables")
public class RegistrationAppAllMonthTimetablesController {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;
    private MonthTimetableService monthTimetableService;
    private TemplateService templateService;

    @Autowired
    public RegistrationAppAllMonthTimetablesController(InstitutionService institutionService,
                                                       RepresentativeService representativeService,
                                                       MonthTimetableService monthTimetableService,
                                                       TemplateService templateService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.monthTimetableService = monthTimetableService;
        this.templateService = templateService;
    }

    @GetMapping
    public List<MonthTimetable> showMonthTimetables(@PathVariable String institutionName,
                                                    @PathVariable String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        return monthTimetableService.findAllMonthTimetablesByRepresentative(representative);
    }

    @GetMapping("/get-next-template")
    public Template getNextTemplate(@PathVariable String institutionName,
                                    @PathVariable String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        YearAndMonth yearAndMonth = monthTimetableService.findLastMonthTimetable(representative);
        return templateService.getNextEmptyTemloate(yearAndMonth);
    }

//    @PostMapping("/get-next-template")      //   todo
//    public MonthTimetable setNextMonthTimetable(@PathVariable String institutionName,
//                                                @PathVariable String representativeName,
//                                                @RequestBody Template template) {
//
//    }

    @GetMapping("/year")
    public List<MonthTimetable> showMonthTimetablesByYear(@PathVariable String institutionName,
                                                          @PathVariable String representativeName,
                                                          @PathVariable int year) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        return monthTimetableService.findMonthTimetablesByRepresentativeAndYear(representative, year);
    }

    @GetMapping("/year/month")
    public MonthTimetable showMonthTimetablesByYearAndMonth(@PathVariable String institutionName,
                                                            @PathVariable String representativeName,
                                                            @PathVariable int year,
                                                            @PathVariable int month) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = representativeService
                .findRepresentativeFromInstitutionByName(institution, representativeName);
        return monthTimetableService.findMonthTimetablesByYearAndMonthAndRepresentative(representative, year, month);
    }
}
