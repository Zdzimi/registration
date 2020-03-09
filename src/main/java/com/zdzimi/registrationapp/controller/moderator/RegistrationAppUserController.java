package com.zdzimi.registrationapp.controller.moderator;

import com.zdzimi.registrationapp.model.Institution;
import com.zdzimi.registrationapp.model.User;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/registration-app/{institutionName}/user")
public class RegistrationAppUserController {

    private InstitutionService institutionService;
    private UserService userService;

    @Autowired
    public RegistrationAppUserController(InstitutionService institutionService, UserService userService) {
        this.institutionService = institutionService;
        this.userService = userService;
    }

    @GetMapping
    public Set<User> showUsers(@PathVariable String institutionName){
        Institution institution = institutionService.findInstitution(institutionName);
        return userService.findUsers(institution);
    }

    @GetMapping("/{username}")
    public User showUser(@PathVariable String institutionName,
                         @PathVariable String username){
        Institution institution = institutionService.findInstitution(institutionName);
        return userService.findUserByName(institution, username);
    }
}
