package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.*;
import com.zdzimi.registrationapp.service.InstitutionService;
import com.zdzimi.registrationapp.service.RepresentativeService;
import com.zdzimi.registrationapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private InstitutionService institutionService;
    private UserService userService;
    private RepresentativeService representativeService;

    @Autowired
    public RegistrationController(InstitutionService institutionService,
                                  UserService userService,
                                  RepresentativeService representativeService) {
        this.institutionService = institutionService;
        this.userService = userService;
        this.representativeService = representativeService;
    }

    @GetMapping("/{institutionName}")
    public Set<Representative> showRepresentatives(@PathVariable String institutionName){
        Institution institution = institutionService.findInstitution(institutionName);
        return representativeService.findRepresentatives(institution);
    }

//    @PostMapping("/{institutionName}")
//    public User createUser(@PathVariable String institutionName, @RequestBody User user){
//        Institution institution = institutionService.findInstitution(institutionName);
//        user.setInstitution(institution);
//        return userService.save(user);
//    }

    @GetMapping("/{institutionName}/{representativeUsername}")      // todo security
    public Set<MonthTimetable> showMonthTimetable(@PathVariable String institutionName,
                                                  @PathVariable String representativeUsername){
        Institution institution = institutionService.findInstitution(institutionName);
        return representativeService.findRepresentativeByName(institution, representativeUsername).getMonthTimetables();
    }
}
