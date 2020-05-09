package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.service.UserLinkService;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.RepresentativeService;
import com.zdzimi.registrationapp.service.entities.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/institutions")
@CrossOrigin
public class RegistrationInstitutions {

    private InstitutionService institutionService;
    private UserService userService;
    private RepresentativeService representativeService;
    private UserLinkService userLinkService;

    @Autowired
    public RegistrationInstitutions(InstitutionService institutionService,
                                    UserService userService,
                                    RepresentativeService representativeService,
                                    UserLinkService userLinkService) {
        this.institutionService = institutionService;
        this.userService = userService;
        this.representativeService = representativeService;
        this.userLinkService = userLinkService;
    }

    @GetMapping
    public List<Institution> showAllInstitutions(@PathVariable String username) {
        List<Institution> institutionList = institutionService.findAll();
        userLinkService.addLinksToInstitutions(institutionList, username);
        return institutionList;
    }

    @GetMapping("/know")
    public List<Institution> showMyInstitution(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        List<Institution> institutionList = institutionService.findByUsers(user);
        userLinkService.addLinksToInstitutions(institutionList, username);
        return institutionList;
    }

    @GetMapping("/{institutionName}")
    public Institution showInstitution(@PathVariable String username, @PathVariable String institutionName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        userLinkService.addLinksToInstitution(institution, username);
        return institution;
    }
}
