package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.Institution;
import com.zdzimi.registrationapp.model.MonthTimetable;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/registration-app/{institutionName}/representative/{representativeName}/timetables")
public class RegistrationAppMonthTimetableController {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;

    @Autowired
    public RegistrationAppMonthTimetableController(InstitutionService institutionService,
                                                   RepresentativeService representativeService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
    }

    @GetMapping
    public Set<MonthTimetable> showMonthTimetables(@PathVariable String institutionName,
                                                   @PathVariable String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        return representativeService.findRepresentativeByName(institution,representativeName).getMonthTimetables();
    }
}
