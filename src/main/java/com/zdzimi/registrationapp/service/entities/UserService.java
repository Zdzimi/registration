package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.UserNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public List<User> findByInstitutions(Institution institution) {
        return userRepo.findByInstitutions(institution);
    }

    public User findUserByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public User findByInstitutionsAndUsername(Institution institution, String username) {
        return userRepo.findByInstitutionsAndUsername(institution, username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User save(User user) {
        return userRepo.save(user);
    }
}
