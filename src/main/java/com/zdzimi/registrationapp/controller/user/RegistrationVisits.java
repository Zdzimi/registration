package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.model.entities.Visit;
import com.zdzimi.registrationapp.service.UserLinkService;
import com.zdzimi.registrationapp.service.entities.UserService;
import com.zdzimi.registrationapp.service.entities.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/registration/{username}/visits")
@CrossOrigin
public class RegistrationVisits {

    private UserService userService;
    private VisitService visitService;
    private UserLinkService userLinkService;

    @Autowired
    public RegistrationVisits(UserService userService, VisitService visitService, UserLinkService userLinkService) {
        this.userService = userService;
        this.visitService = visitService;
        this.userLinkService = userLinkService;
    }

    @GetMapping
    public List<Visit> showAllVisits(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        List<Visit> visitList = visitService.findByUser(user);
        userLinkService.addLinksToMyVisits(visitList, username);
        return visitList;
    }

    @GetMapping("/{visitId}")
    public Set<Visit> showVisit(@PathVariable String username, @PathVariable long visitId) {
        User user = userService.findUserByUsername(username);
        Visit visit = visitService.findByUserAndId(user, visitId);
        userLinkService.addLinkToCancelAndBack(visit, username);
        return Collections.singleton(visit);
    }

    @GetMapping("/{visitId}/cancel")
    public Visit cancelYourVisit(@PathVariable String username, @PathVariable long visitId) {
        User user = userService.findUserByUsername(username);
        return visitService.cancelVisit(user, visitId);
    }
}
