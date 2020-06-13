package com.zdzimi.registrationapp.controller.representative;

import com.zdzimi.registrationapp.model.*;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.service.RepresentativeLinkService;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.PlaceService;
import com.zdzimi.registrationapp.service.entities.RepresentativeService;
import com.zdzimi.registrationapp.service.entities.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/registration-app")
@CrossOrigin
public class RegistrationAppController {

    private InstitutionService institutionService;
    private UserService userService;
    private RepresentativeService representativeService;
    private RepresentativeLinkService representativeLinkService;

    @Autowired
    public RegistrationAppController(InstitutionService institutionService,
                                     UserService userService,
                                     RepresentativeService representativeService,
                                     RepresentativeLinkService representativeLinkService) {
        this.institutionService = institutionService;
        this.userService = userService;
        this.representativeService = representativeService;
        this.representativeLinkService = representativeLinkService;
    }

    @PostMapping
    public Institution createNewInstitution(@RequestBody InitialObject initialObject){
        Institution institution = initialObject.getInstitution();
        User user = userService.findUserByUsername(initialObject.getUser().getUsername());
        institutionService.save(institution);
        Representative representative = representativeService.createRepresentativeFromUser(user);
        representative.getWorkPlaces().add(institution);
        representativeService.save(representative);
        return institutionService.findByInstitutionName(institution.getInstitutionName());
    }

    @GetMapping("/{institutionName}")
    public Set<Institution> findInstitutionByName(@PathVariable String institutionName){
        Institution institution = institutionService.findByInstitutionName(institutionName);
        representativeLinkService.addLinkToInstitution(institution);
        return Collections.singleton(institution);
    }
}
