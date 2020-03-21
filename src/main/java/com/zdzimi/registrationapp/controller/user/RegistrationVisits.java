package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.model.entities.Visit;
import com.zdzimi.registrationapp.service.UserService;
import com.zdzimi.registrationapp.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration/{username}/visits")
public class RegistrationVisits {

    private UserService userService;
    private VisitService visitService;

    @Autowired
    public RegistrationVisits(UserService userService, VisitService visitService) {
        this.userService = userService;
        this.visitService = visitService;
    }

    @GetMapping
    public List<Visit> showAllVisits(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return visitService.findAllByUser(user);
    }

    @GetMapping("/{visitId}")
    public Visit showVisit(@PathVariable String username, @PathVariable long visitId) {
        User user = userService.findUserByUsername(username);
        return visitService.findByUserAndId(user, visitId);
    }

    @PostMapping("/{visitId}")
    public Visit cancelYourVisit(@PathVariable String username, @PathVariable long visitId) {
        User user = userService.findUserByUsername(username);
        return visitService.cancelVisit(user, visitId);
    }
}
