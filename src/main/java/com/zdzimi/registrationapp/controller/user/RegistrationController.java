package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.service.UserLinkService;
import com.zdzimi.registrationapp.service.entities.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;
    private UserLinkService userLinkService;

    @Autowired
    public RegistrationController(UserService userService, UserLinkService userLinkService) {
        this.userService = userService;
        this.userLinkService = userLinkService;
    }

    @PostMapping
    public User createNewUser(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/{username}")
    public User showUser(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        userLinkService.addLinks(user);
        return user;
    }
}
