package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.service.UserLinkService;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/registration/{username}/institutions/{institutionName}/representatives")
@CrossOrigin
public class RegistrationRepresentatives {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;
    private UserLinkService userLinkService;

    @Autowired
    public RegistrationRepresentatives(InstitutionService institutionService,
                                       RepresentativeService representativeService,
                                       UserLinkService userLinkService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.userLinkService = userLinkService;
    }

    @GetMapping
    public List<Representative> showRepresentatives(@PathVariable String username, @PathVariable String institutionName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        List<Representative> representativeList = representativeService.findByWorkPlaces(institution);
        userLinkService.addLinksToRepresentatives(representativeList, username, institutionName);
        return representativeList;
    }

    @GetMapping("/{representativeName}")
    public Set<Representative> showRepresentative(@PathVariable String username, @PathVariable String institutionName,
                                                  @PathVariable String representativeName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService.findByWorkPlacesAndUsername(institution, representativeName);
        userLinkService.addLinksToRepresentative(representative, username, institutionName);
        return Collections.singleton(representative);
    }
}
