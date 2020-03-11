package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.exception.UserNotFoundException;
import com.zdzimi.registrationapp.model.Institution;
import com.zdzimi.registrationapp.model.User;
import com.zdzimi.registrationapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Set<User> findUsers(Institution institution) {
        return institution.getUsers();
    }

    public User findUserByName(Institution institution, String username) {
        return institution.getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny().orElseThrow(() -> new UserNotFoundException(username));
    }

    public User save(User user) {
        return userRepo.save(user);
    }
}
