package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.service.UserLinkService;
import com.zdzimi.registrationapp.service.entities.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {

    private UserService userService;
    private UserLinkService userLinkService;

    @Autowired
    public RegistrationController(UserService userService, UserLinkService userLinkService) {
        this.userService = userService;
        this.userLinkService = userLinkService;
    }

    @PostMapping("/new-user")
    public User createNewUser(@RequestBody User user){
        return userService.save(user);
    }

    @PostMapping("/update-user")
//    @PreAuthorize("targetObject[0].username == principal.username")
    public User updateUser(@RequestBody User[] users){
        return userService.update(users);
    }

    @GetMapping("/{username}")
//    @PostAuthorize("returnObject[0].username == principal.username")
    public Set<User> showUser(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        userLinkService.addLinks(user);
        return Collections.singleton(user);
    }
}
