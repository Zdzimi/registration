package com.zdzimi.registrationapp.controller.representative;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.service.RepresentativeLinkService;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.RepresentativeService;
import com.zdzimi.registrationapp.service.entities.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/registration-app/{institutionName}/representatives")
@CrossOrigin
public class RegistrationAppRepresentativeController {

    private InstitutionService institutionService;
    private RepresentativeService representativeService;
    private UserService userService;
    private RepresentativeLinkService representativeLinkService;

    @Autowired
    public RegistrationAppRepresentativeController(InstitutionService institutionService,
                                                   RepresentativeService representativeService,
                                                   UserService userService,
                                                   RepresentativeLinkService representativeLinkService) {
        this.institutionService = institutionService;
        this.representativeService = representativeService;
        this.userService = userService;
        this.representativeLinkService = representativeLinkService;
    }

    @GetMapping
    public List<Representative> showRepresentatives(@PathVariable String institutionName){
        Institution institution = institutionService.findByInstitutionName(institutionName);
        List<Representative> representativeList = representativeService.findByWorkPlaces(institution);
        representativeLinkService.addLinksToAllRepresentatives(representativeList, institutionName);
        return representativeList;
    }

    @PostMapping
    public Representative addRepresentative(@PathVariable String institutionName,
                                            @RequestBody String representativeName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        User user = userService.findUserByUsername(representativeName);
        Representative representative = representativeService.getRepresentativeByUser(user);
        representative.getWorkPlaces().add(institution);
        return representativeService.save(representative);
    }

    @GetMapping("/{representativeName}")
    public Set<Representative> findRepresentative(@PathVariable String institutionName,
                                                  @PathVariable String representativeName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        Representative representative = representativeService.findByWorkPlacesAndUsername(institution, representativeName);
        representativeLinkService.addLinksRepresentative(representative, institutionName);
        return Collections.singleton(representative);
    }
}
