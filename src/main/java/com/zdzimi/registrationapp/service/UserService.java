package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.comparator.UserComparator;
import com.zdzimi.registrationapp.exception.UserNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findUsersFromInstitution(Institution institution) {
        return institution.getUsers().stream()
                .sorted(new UserComparator())
                .collect(Collectors.toList());
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
