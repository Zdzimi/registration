package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.service.UserLinkService;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/registration/{username}/institutions")
@CrossOrigin
public class RegistrationInstitutions {

    private InstitutionService institutionService;
    private UserService userService;
    private UserLinkService userLinkService;

    @Autowired
    public RegistrationInstitutions(InstitutionService institutionService,
                                    UserService userService,
                                    UserLinkService userLinkService) {
        this.institutionService = institutionService;
        this.userService = userService;
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
    public Set<Institution> showInstitution(@PathVariable String username, @PathVariable String institutionName) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        userLinkService.addLinksToInstitution(institution, username);
        return Collections.singleton(institution);
    }
}
