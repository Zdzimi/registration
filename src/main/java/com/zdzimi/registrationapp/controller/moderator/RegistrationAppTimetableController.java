package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration-app/{institutionName}/representative/{representativeName}/timetable")
public class RegistrationAppTimetableController {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;

    @Autowired
    public RegistrationAppTimetableController(InstitutionService institutionService,
                                              RepresentativeService representativeService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
    }

}
