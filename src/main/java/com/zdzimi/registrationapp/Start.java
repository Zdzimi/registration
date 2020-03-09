//package com.zdzimi.registrationapp;
//
//import com.zdzimi.registrationapp.model.*;
//import com.zdzimi.registrationapp.repository.InstitutonRepo;
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
//    private InstitutonRepo institutonRepo;
//    private UserRepo userRepo;
//    private RepresentativeRepo representativeRepo;
//    private PlaceRepo placeRepo;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public Start(InstitutonRepo institutonRepo,
//                 UserRepo userRepo,
//                 RepresentativeRepo representativeRepo,
//                 PlaceRepo placeRepo,
//                 PasswordEncoder passwordEncoder) {
//        this.institutonRepo = institutonRepo;
//        this.userRepo = userRepo;
//        this.representativeRepo = representativeRepo;
//        this.placeRepo = placeRepo;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void startApp(){
//        Institution institution = new Institution("inst");
//        institutonRepo.save(institution);
//
//        Representative jan = new Representative(
//                "Jan",
//                "jan@jan.com",
//                passwordEncoder.encode("jan123"),
//                Role.ROLE_ADMIN,
//                institution);
//        representativeRepo.save(jan);
//
//        Representative michal = new Representative(
//                "Michal",
//                "michal@michal.com",
//                passwordEncoder.encode("michal123"),
//                Role.ROLE_ADMIN,
//                institution);
//        representativeRepo.save(michal);
//
//        User ula = new Representative(
//                "Ula",
//                "ula@ula.com",
//                passwordEncoder.encode("ula123"),
//                Role.ROLE_USER,
//                institution);
//        userRepo.save(ula);
//
//        User ala = new Representative(
//                "Ala",
//                "ala@ala.com",
//                passwordEncoder.encode("ala123"),
//                Role.ROLE_USER,
//                institution);
//        userRepo.save(ala);
//
//        Place place = new Place("no_1", institution);
//        Place place2 = new Place("no_2", institution);
//        placeRepo.save(place);
//        placeRepo.save(place2);
//    }
//}
