package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.UserNotFoundException;
import com.zdzimi.registrationapp.security.Role;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(Role.ROLE_USER);
        return userRepo.save(newUser);
    }

    public User update(User[] users) {
        User user = userRepo.findById(users[0].getUserId()).orElseThrow(() -> new UserNotFoundException("user"));
        if (passwordEncoder.matches(users[0].getPassword(), user.getPassword())) {
            user.setUsername(users[1].getUsername());
            user.setName(users[1].getName());
            user.setSurname(users[1].getSurname());
            user.setEmail(users[1].getEmail());
            user.setPassword(passwordEncoder.encode(users[1].getPassword()));
            userRepo.save(user);
        }
        return user;
    }
}
