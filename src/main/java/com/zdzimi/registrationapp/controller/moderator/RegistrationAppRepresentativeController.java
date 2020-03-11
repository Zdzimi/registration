package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.Institution;
import com.zdzimi.registrationapp.model.Representative;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/registration-app/{institutionName}/representative")
public class RegistrationAppRepresentativeController {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;

    @Autowired
    public RegistrationAppRepresentativeController(InstitutionService institutionService,
                                                   RepresentativeService representativeService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
    }

    @GetMapping
    public Set<Representative> showRepresentatives(@PathVariable String institutionName){
        Institution institution = institutionService.findInstitution(institutionName);
        return representativeService.findRepresentatives(institution);
    }

    @PostMapping
    public Representative addRepresentative(@PathVariable String institutionName,
                                            @RequestBody Representative representative){
        Institution institution = institutionService.findInstitution(institutionName);
        representative.getWorkPlaces().add(institution);
        return representativeService.save(representative);
    }

    @GetMapping("/{representativeName}")
    public Representative findRepresentative(@PathVariable String institutionName,
                                             @PathVariable String representativeName) {
        Institution institution = institutionService.findInstitution(institutionName);
        return representativeService.findRepresentativeByName(institution, representativeName);
    }
}
