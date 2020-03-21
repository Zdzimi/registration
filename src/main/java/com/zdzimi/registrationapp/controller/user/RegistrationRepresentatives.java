package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institutions/{institutionName}/representatives")
public class RegistrationRepresentatives {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;

    @Autowired
    public RegistrationRepresentatives(InstitutionService institutionService,
                                       RepresentativeService representativeService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
    }

    @GetMapping
    public List<Representative> showRepresentatives(@PathVariable String institutionName) {
        Institution institution = institutionService.findInstitution(institutionName);
        return representativeService.findRepresentativesFromInstitution(institution);
    }

    @GetMapping("/{representativeName}")
    public Representative showRepresentative(@PathVariable String institutionName,
                                             @PathVariable String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        return representativeService.findRepresentativeFromInstitutionByName(institution, representativeName);
    }
}
