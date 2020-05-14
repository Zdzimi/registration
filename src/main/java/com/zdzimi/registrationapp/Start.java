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
//        Institution barber = new Institution();
//        barber.setInstitutionName("barber");
//        barber.setProvince("Dolnośląskie");
//        barber.setCity("Wrocław");
//        barber.setStreet("Karmelkowa");
//        barber.setGateNumber("33-B");
//        barber.setPremisesNumber("2");
//        barber.setTypeOfService("Kreacja fryzur");
//        barber.setDescription("Super fryzury robimy");
//        institutionRepo.save(barber);
//
//        Representative anna = new Representative(
//                "Anna",
//                "Warchol",
//                "anna_warchol@fryzjer.pl",
//                passwordEncoder.encode("anna123"),
//                Role.ROLE_USER);
//        anna.getWorkPlaces().add(barber);
//        representativeRepo.save(anna);
//
//        Representative tom = new Representative(
//                "Tomek",
//                "Psikuta",
//                "tomek_psikuta@fryzjer.pl",
//                passwordEncoder.encode("tomek123"),
//                Role.ROLE_USER);
//        tom.getWorkPlaces().add(barber);
//        representativeRepo.save(tom);
//
//        User ula = new User(
//                "Ula",
//                "Zych",
//                "ula_zych@gmail.com",
//                passwordEncoder.encode("ula123"),
//                Role.ROLE_USER);
//        ula.getInstitutions().add(barber);
//        userRepo.save(ula);
//
//        User ala = new User(
//                "Ala",
//                "Kułakowska",
//                "ala_kulak@gmail.com",
//                passwordEncoder.encode("ala123"),
//                Role.ROLE_USER);
//        ala.getInstitutions().add(barber);
//        userRepo.save(ala);
//
//        Place place = new Place("stanowisko nr 1", barber);
//        Place place2 = new Place("stanowisko nr 2", barber);
//        placeRepo.save(place);
//        placeRepo.save(place2);
//
//        //====================
//
//        Institution tattoo = new Institution();
//        tattoo.setInstitutionName("tattoo");
//        tattoo.setProvince("Dolnośląskie");
//        tattoo.setCity("Wrocław");
//        tattoo.setStreet("Swobodna");
//        tattoo.setGateNumber("4");
//        tattoo.setTypeOfService("Salon tatuażu");
//        tattoo.setDescription("Dziary wszelakie");
//        institutionRepo.save(tattoo);
//
//        Representative ola = new Representative(
//                "Ola",
//                "Jajko",
//                "ola_jajko@tattoo.com",
//                passwordEncoder.encode("ola123"),
//                Role.ROLE_USER);
//        ola.getWorkPlaces().add(tattoo);
//        representativeRepo.save(ola);
//
//        Representative john = new Representative(
//                "John",
//                "Snith",
//                "john@tattoo.com",
//                passwordEncoder.encode("john123"),
//                Role.ROLE_USER);
//        john.getWorkPlaces().add(tattoo);
//        representativeRepo.save(john);
//
//        User janina = new User(
//                "Janina",
//                "Sobolewska",
//                "janina@gmail.com",
//                passwordEncoder.encode("janina123"),
//                Role.ROLE_USER);
//        janina.getInstitutions().add(tattoo);
//        userRepo.save(janina);
//
//        User jan = new User(
//                "Jan",
//                "Wojtaszko",
//                "wojtaszkojan@gmail.com",
//                passwordEncoder.encode("jan123"),
//                Role.ROLE_USER);
//        jan.getInstitutions().add(tattoo);
//        jan.getInstitutions().add(barber);
//        userRepo.save(jan);
//
//        Place placeZ = new Place("leżąnka 1", tattoo);
//        Place placeZ2 = new Place("leżąnka 2", tattoo);
//        placeRepo.save(placeZ);
//        placeRepo.save(placeZ2);
//    }
//}
