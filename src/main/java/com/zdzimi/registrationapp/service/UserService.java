package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.exception.UserNotFoundException;
import com.zdzimi.registrationapp.model.Institution;
import com.zdzimi.registrationapp.model.User;
import com.zdzimi.registrationapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Set<User> findUsersFromInstitution(Institution institution) {
        return institution.getUsers();
    }

    public User findUserFromInstitutionByName(Institution institution, String username) {
        return institution.getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny().orElseThrow(() -> new UserNotFoundException(username));
    }

    public User findUserByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public List<User> findAllUsers(){
        return userRepo.findAll();
    }

    public User save(User user) {
        return userRepo.save(user);
    }
}
