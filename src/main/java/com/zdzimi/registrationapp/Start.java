//package com.zdzimi.registrationapp;
//
//import com.zdzimi.registrationapp.model.entities.Institution;
//import com.zdzimi.registrationapp.model.entities.Place;
//import com.zdzimi.registrationapp.model.entities.Representative;
//import com.zdzimi.registrationapp.model.entities.User;
//import com.zdzimi.registrationapp.repository.InstitutionRepo;
//import com.zdzimi.registrationapp.repository.PlaceRepo;
//import com.zdzimi.registrationapp.repository.RepresentativeRepo;
//import com.zdzimi.registrationapp.repository.UserRepo;
//import com.zdzimi.registrationapp.security.Role;
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
//
//        Institution barber = new Institution();
//        barber.setInstitutionName("fryzjer");
//        barber.setProvince("Dolnośląskie");
//        barber.setCity("Wrocław");
//        barber.setStreet("Karmelkowa");
//        barber.setGateNumber("33-B");
//        barber.setPremisesNumber("2");
//        barber.setTypeOfService("Kreacja fryzur");
//        barber.setDescription("Super fryzury robimy");
//        institutionRepo.save(barber);
//
//        Representative lucyna = new Representative(
//                "Lucyna",
//                "Lucyna",
//                "Warchol",
//                "lucynaw@fryzjer.pl",
//                passwordEncoder.encode("pass"),
//                Role.ROLE_USER);
//        lucyna.getWorkPlaces().add(barber);
//        representativeRepo.save(lucyna);
//
//        Representative tom = new Representative(
//                "Tomek",
//                "Tomek",
//                "Psikuta",
//                "tomekpsikuta@fryzjer.pl",
//                passwordEncoder.encode("pass"),
//                Role.ROLE_USER);
//        tom.getWorkPlaces().add(barber);
//        representativeRepo.save(tom);
//
//        User janusz = new User(
//                "Janusz",
//                "Janusz",
//                "Nosacz",
//                "janusz@gmail.com",
//                passwordEncoder.encode("pass"),
//                Role.ROLE_USER);
//        janusz.getInstitutions().add(barber);
//        userRepo.save(janusz);
//
//        User ula = new User(
//                "Ula",
//                "Ula",
//                "Kułakowska",
//                "ula_kulak@gmail.com",
//                passwordEncoder.encode("pass"),
//                Role.ROLE_USER);
//        ula.getInstitutions().add(barber);
//        userRepo.save(ula);
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
//        Representative ala = new Representative(
//                "Ala",
//                "Ala",
//                "Jajko",
//                "alajajko@tattoo.com",
//                passwordEncoder.encode("pass"),
//                Role.ROLE_USER);
//        ala.getWorkPlaces().add(tattoo);
//        representativeRepo.save(ala);
//
//        Representative ola = new Representative(
//                "Ola",
//                "Ola",
//                "Szum",
//                "olaszum@tattoo.com",
//                passwordEncoder.encode("pass"),
//                Role.ROLE_USER);
//        ola.getWorkPlaces().add(tattoo);
//        representativeRepo.save(ola);
//
//        User anna = new User(
//                "Anna",
//                "Anna",
//                "Sobolewska",
//                "annasob@gmail.com",
//                passwordEncoder.encode("pass"),
//                Role.ROLE_USER);
//        anna.getInstitutions().add(tattoo);
//        userRepo.save(anna);
//
//        User jan = new User(
//                "Jan",
//                "Jan",
//                "Seweryn",
//                "sewerynjan@gmail.com",
//                passwordEncoder.encode("pass"),
//                Role.ROLE_USER);
//        jan.getInstitutions().add(tattoo);
//        jan.getInstitutions().add(barber);
//        userRepo.save(jan);
//
//        Place placeZ = new Place("le 1", tattoo);
//        Place placeZ2 = new Place("le 2", tattoo);
//        placeRepo.save(placeZ);
//        placeRepo.save(placeZ2);
//    }
//}
