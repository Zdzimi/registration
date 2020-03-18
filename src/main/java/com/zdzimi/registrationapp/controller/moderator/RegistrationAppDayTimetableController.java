package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.service.DayTimetableService;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.MonthTimetableService;
import com.zdzimi.registrationapp.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration-app/{institutionName}/representative/{representativeName}/timetable/{year}/{month}/{day}/")
public class RegistrationAppDayTimetableController {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;
    private MonthTimetableService monthTimetableService;
    private DayTimetableService dayTimetableService;

    @Autowired
    public RegistrationAppDayTimetableController(InstitutionService institutionService,
                                                 RepresentativeService representativeService,
                                                 MonthTimetableService monthTimetableService,
                                                 DayTimetableService dayTimetableService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.monthTimetableService = monthTimetableService;
        this.dayTimetableService = dayTimetableService;
    }




}
