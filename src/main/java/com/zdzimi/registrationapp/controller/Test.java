package com.zdzimi.registrationapp.controller;

import com.zdzimi.registrationapp.model.Representative;
import com.zdzimi.registrationapp.model.User;
import com.zdzimi.registrationapp.service.RepresentativeService;
import com.zdzimi.registrationapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
public class Test {

    private UserService userService;
    private RepresentativeService representativeService;

    @Autowired
    public Test(UserService userService, RepresentativeService representativeService) {
        this.userService = userService;
        this.representativeService = representativeService;
    }

    @GetMapping("/rep")
    public String testRepresentative(){
        return "Test representative";
    }

    @GetMapping("/user")
    public String testUser(){
        return "Test user";
    }

    @GetMapping("/representatives")
    public List<Representative> getAllRepresentatives(){
        return representativeService.findAllRepresentatives();
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("user/{username}")
    @PostAuthorize("returnObject.username == principal.username")
    public User findUser(@PathVariable String username){
        return userService.findUserByUsername(username);
    }
}
