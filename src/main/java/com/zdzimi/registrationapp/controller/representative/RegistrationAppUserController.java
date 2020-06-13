package com.zdzimi.registrationapp.controller.representative;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.model.entities.Visit;
import com.zdzimi.registrationapp.service.RepresentativeLinkService;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.UserService;
import com.zdzimi.registrationapp.service.entities.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/registration-app/{institutionName}/users")
@CrossOrigin
public class RegistrationAppUserController {

    private InstitutionService institutionService;
    private UserService userService;
    private RepresentativeLinkService representativeLinkService;
    private VisitService visitService;

    @Autowired
    public RegistrationAppUserController(InstitutionService institutionService,
                                         UserService userService,
                                         RepresentativeLinkService representativeLinkService, VisitService visitService) {
        this.institutionService = institutionService;
        this.userService = userService;
        this.representativeLinkService = representativeLinkService;
        this.visitService = visitService;
    }

    @GetMapping
    public List<User> showUsers(@PathVariable String institutionName){
        Institution institution = institutionService.findByInstitutionName(institutionName);
        List<User> userList = userService.findByInstitutions(institution);
        representativeLinkService.addLinksToUsers(userList, institutionName);
        return userList;
    }

    @GetMapping("/{username}")
    public Set<User> showUser(@PathVariable String institutionName,
                              @PathVariable String username){
        Institution institution = institutionService.findByInstitutionName(institutionName);
        User user = userService.findByInstitutionsAndUsername(institution, username);
        representativeLinkService.addLinksToUsersVisits(user, institution);
        return Collections.singleton(user);
    }

    @GetMapping("/{username}/{visitId}")
    public Set<Visit> showUsersVisit(@PathVariable String institutionName,
                                     @PathVariable String username,
                                     @PathVariable long visitId) {
        Institution institution = institutionService.findByInstitutionName(institutionName);
        User user = userService.findByInstitutionsAndUsername(institution, username);
        Visit visit = visitService.findByUserAndId(user, visitId);
        representativeLinkService.addLinksToUsersVisit(visit, institution);
        return Collections.singleton(visit);
    }
}
