package com.zdzimi.registrationapp;

import com.zdzimi.registrationapp.model.*;
import com.zdzimi.registrationapp.repository.InstitutonRepo;
import com.zdzimi.registrationapp.repository.PlaceRepo;
import com.zdzimi.registrationapp.repository.RepresentativeRepo;
import com.zdzimi.registrationapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class Start {

    private InstitutonRepo institutonRepo;
    private UserRepo userRepo;
    private RepresentativeRepo representativeRepo;
    private PlaceRepo placeRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public Start(InstitutonRepo institutonRepo,
                 UserRepo userRepo,
                 RepresentativeRepo representativeRepo,
                 PlaceRepo placeRepo,
                 PasswordEncoder passwordEncoder) {
        this.institutonRepo = institutonRepo;
        this.userRepo = userRepo;
        this.representativeRepo = representativeRepo;
        this.placeRepo = placeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startApp(){
        Institution institution = new Institution("inst");
        institutonRepo.save(institution);

        Representative jan = new Representative(
                "Jan",
                "jan@jan.com",
                passwordEncoder.encode("jan123"),
                Role.ROLE_USER);
        jan.getWorkPlaces().add(institution);
        representativeRepo.save(jan);

        Representative michal = new Representative(
                "Michal",
                "michal@michal.com",
                passwordEncoder.encode("michal123"),
                Role.ROLE_ADMIN);
        michal.getWorkPlaces().add(institution);
        representativeRepo.save(michal);

        User ula = new User(
                "Ula",
                "ula@ula.com",
                passwordEncoder.encode("ula123"),
                Role.ROLE_USER);
        ula.getInstitutions().add(institution);
        userRepo.save(ula);

        User ala = new User(
                "Ala",
                "ala@ala.com",
                passwordEncoder.encode("ala123"),
                Role.ROLE_USER);
        ala.getInstitutions().add(institution);
        userRepo.save(ala);

        Place place = new Place("no_1", institution);
        Place place2 = new Place("no_2", institution);
        placeRepo.save(place);
        placeRepo.save(place2);

        //====================

        Institution institutionZ = new Institution("instZ");
        institutonRepo.save(institutionZ);

        Representative janZ = new Representative(
                "JanZ",
                "jan@jan.com",
                passwordEncoder.encode("jan123"),
                Role.ROLE_USER);
        jan.getWorkPlaces().add(institutionZ);
        representativeRepo.save(janZ);

        Representative michalZ = new Representative(
                "MichalZ",
                "michal@michal.com",
                passwordEncoder.encode("michal123"),
                Role.ROLE_ADMIN);
        michal.getWorkPlaces().add(institutionZ);
        representativeRepo.save(michalZ);

        User ulaZ = new User(
                "UlaZ",
                "ula@ula.com",
                passwordEncoder.encode("ula123"),
                Role.ROLE_USER);
        ula.getInstitutions().add(institutionZ);
        userRepo.save(ulaZ);

        User alaZ = new User(
                "AlaZ",
                "ala@ala.com",
                passwordEncoder.encode("ala123"),
                Role.ROLE_USER);
        ala.getInstitutions().add(institutionZ);
        userRepo.save(alaZ);

        Place placeZ = new Place("no_1", institutionZ);
        Place placeZ2 = new Place("no_2", institutionZ);
        placeRepo.save(placeZ);
        placeRepo.save(placeZ2);
    }
}
