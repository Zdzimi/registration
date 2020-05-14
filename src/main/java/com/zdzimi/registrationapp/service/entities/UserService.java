package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.UserNotFoundException;
import com.zdzimi.registrationapp.model.Role;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword2(user.getPassword());
        user.setRole(Role.ROLE_USER);
        return userRepo.save(user);
    }

    public User update(User newUser) {
        User oldUser = userRepo.findById(newUser.getUserId())
                .orElseThrow(() -> new UserNotFoundException("user"));
        if (passwordEncoder.matches(newUser.getPassword2(), oldUser.getPassword())) {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setPassword2(newUser.getPassword());
            userRepo.save(newUser);
            return newUser;
        }
        return oldUser;
    }
}
