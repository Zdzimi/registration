package com.zdzimi.registrationapp.controller.representative;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import com.zdzimi.registrationapp.service.entities.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<User> showUsers(@PathVariable String institutionName){
        Institution institution = institutionService.findByInstitutionName(institutionName);
        return userService.findByInstitutions(institution);
    }

    @GetMapping("/{username}")
    public User showUser(@PathVariable String institutionName,
                         @PathVariable String username){
        Institution institution = institutionService.findByInstitutionName(institutionName);
        return userService.findByInstitutionsAndUsername(institution, username);
    }
}
