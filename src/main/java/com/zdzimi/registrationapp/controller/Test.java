package com.zdzimi.registrationapp.controller;

import com.zdzimi.registrationapp.model.User;
import com.zdzimi.registrationapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class Test {

    private UserRepo userRepo;

    @Autowired
    public Test(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/rep")
    public String testRepresentative(){
        return "Test representative";
    }

    @GetMapping("/user")
    public String testUser(){
        return "Test user";
    }

    @GetMapping("user/{username}")
    @PostAuthorize("returnObject.username == principal.username")
    public User findUser(@PathVariable String username){
        return userRepo.findByUsername(username).orElse(null);
    }
}
