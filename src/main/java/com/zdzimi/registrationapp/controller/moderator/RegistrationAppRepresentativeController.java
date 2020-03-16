package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.RepresentativeService;
import com.zdzimi.registrationapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration-app/{institutionName}/representative")
public class RegistrationAppRepresentativeController {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;
    private UserService userService;

    @Autowired
    public RegistrationAppRepresentativeController(InstitutionService institutionService,
                                                   RepresentativeService representativeService,
                                                   UserService userService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.userService = userService;
    }

    @GetMapping
    public List<Representative> showRepresentatives(@PathVariable String institutionName){
        Institution institution = institutionService.findInstitution(institutionName);
        return representativeService.findRepresentativesFromInstitution(institution);
    }

    @PostMapping
    public Representative addRepresentative(@PathVariable String institutionName,
                                            @RequestBody String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        Representative representative = (Representative) userService.findUserByUsername(representativeName);
        representative.getWorkPlaces().add(institution);
        return representativeService.save(representative);
    }

    @GetMapping("/{representativeName}")
    public Representative findRepresentative(@PathVariable String institutionName,
                                             @PathVariable String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        return representativeService.findRepresentativeFromInstitutionByName(institution, representativeName);
    }
}
