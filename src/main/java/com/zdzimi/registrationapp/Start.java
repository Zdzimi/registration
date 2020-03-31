//package com.zdzimi.registrationapp;
//
//import com.zdzimi.registrationapp.model.*;
//import com.zdzimi.registrationapp.model.entities.Institution;
//import com.zdzimi.registrationapp.model.entities.Place;
//import com.zdzimi.registrationapp.model.entities.Representative;
//import com.zdzimi.registrationapp.model.entities.User;
//import com.zdzimi.registrationapp.repository.InstitutionRepo;
//import com.zdzimi.registrationapp.repository.PlaceRepo;
//import com.zdzimi.registrationapp.repository.RepresentativeRepo;
//import com.zdzimi.registrationapp.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class Start {
//
//    private InstitutionRepo institutionRepo;
//    private UserRepo userRepo;
//    private RepresentativeRepo representativeRepo;
//    private PlaceRepo placeRepo;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public Start(InstitutionRepo institutionRepo,
//                 UserRepo userRepo,
//                 RepresentativeRepo representativeRepo,
//                 PlaceRepo placeRepo,
//                 PasswordEncoder passwordEncoder) {
//        this.institutionRepo = institutionRepo;
//        this.userRepo = userRepo;
//        this.representativeRepo = representativeRepo;
//        this.placeRepo = placeRepo;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void startApp(){
//        Institution institution = new Institution("inst");
//        institutionRepo.save(institution);
//
//        Representative jan = new Representative(
//                "Jan",
//                "Aron",
//                "jan@jan.com",
//                passwordEncoder.encode("jan123"),
//                Role.ROLE_USER);
//        jan.getWorkPlaces().add(institution);
//        representativeRepo.save(jan);
//
//        Representative michal = new Representative(
//                "Michal",
//                "Aron",
//                "michal@michal.com",
//                passwordEncoder.encode("michal123"),
//                Role.ROLE_USER);
//        michal.getWorkPlaces().add(institution);
//        representativeRepo.save(michal);
//
//        User ula = new User(
//                "Ula",
//                "Zych",
//                "ula@ula.com",
//                passwordEncoder.encode("ula123"),
//                Role.ROLE_USER);
//        ula.getInstitutions().add(institution);
//        userRepo.save(ula);
//
//        User ala = new User(
//                "Ala",
//                "Zych",
//                "ala@ala.com",
//                passwordEncoder.encode("ala123"),
//                Role.ROLE_USER);
//        ala.getInstitutions().add(institution);
//        userRepo.save(ala);
//
//        Place place = new Place("no_10", institution);
//        Place place2 = new Place("no_2", institution);
//        placeRepo.save(place);
//        placeRepo.save(place2);
//
//        //====================
//
//        Institution institutionZ = new Institution("instZ");
//        institutionRepo.save(institutionZ);
//
//        Representative janZ = new Representative(
//                "Ola",
//                "Jajko",
//                "jan@jan.com",
//                passwordEncoder.encode("jan123"),
//                Role.ROLE_USER);
//        janZ.getWorkPlaces().add(institutionZ);
//        representativeRepo.save(janZ);
//
//        Representative michalZ = new Representative(
//                "MichalZ",
//                "Jajko",
//                "michal@michal.com",
//                passwordEncoder.encode("michal123"),
//                Role.ROLE_USER);
//        michalZ.getWorkPlaces().add(institutionZ);
//        representativeRepo.save(michalZ);
//
//        User ulaZ = new User(
//                "UlaZ",
//                "Psikuta",
//                "ula@ula.com",
//                passwordEncoder.encode("ula123"),
//                Role.ROLE_USER);
//        ulaZ.getInstitutions().add(institutionZ);
//        userRepo.save(ulaZ);
//
//        User alaZ = new User(
//                "AlaW",
//                "Psikuta",
//                "ala@ala.com",
//                passwordEncoder.encode("ala123"),
//                Role.ROLE_USER);
//        alaZ.getInstitutions().add(institutionZ);
//        userRepo.save(alaZ);
//
//        Place placeZ = new Place("no_1", institutionZ);
//        Place placeZ2 = new Place("no_2", institutionZ);
//        placeRepo.save(placeZ);
//        placeRepo.save(placeZ2);
//    }
//}
