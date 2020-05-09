package com.zdzimi.registrationapp;

import com.zdzimi.registrationapp.model.*;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.repository.InstitutionRepo;
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

    private InstitutionRepo institutionRepo;
    private UserRepo userRepo;
    private RepresentativeRepo representativeRepo;
    private PlaceRepo placeRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public Start(InstitutionRepo institutionRepo,
                 UserRepo userRepo,
                 RepresentativeRepo representativeRepo,
                 PlaceRepo placeRepo,
                 PasswordEncoder passwordEncoder) {
        this.institutionRepo = institutionRepo;
        this.userRepo = userRepo;
        this.representativeRepo = representativeRepo;
        this.placeRepo = placeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startApp(){
        Institution baber = new Institution();
        baber.setInstitutionName("fryzjer");
        baber.setProvince("Dolnośląskie");
        baber.setCity("Wrocław");
        baber.setStreet("Karmelkowa");
        baber.setGateNumber("33-B");
        baber.setPremisesNumber("2");
        baber.setTypeOfService("Kreacja fryzur");
        baber.setDescription("Super fryzury robimy");
        institutionRepo.save(baber);

        Representative anna = new Representative(
                "Anna",
                "Warchol",
                "anna_warchol@fryzjer.pl",
                passwordEncoder.encode("anna123"),
                Role.ROLE_USER);
        anna.getWorkPlaces().add(baber);
        representativeRepo.save(anna);

        Representative tom = new Representative(
                "Tomek",
                "Psikuta",
                "tomek_psikuta@fryzjer.pl",
                passwordEncoder.encode("tomek123"),
                Role.ROLE_USER);
        tom.getWorkPlaces().add(baber);
        representativeRepo.save(tom);

        User ula = new User(
                "Ula",
                "Zych",
                "ula_zych@gmail.com",
                passwordEncoder.encode("ula123"),
                Role.ROLE_USER);
        ula.getInstitutions().add(baber);
        userRepo.save(ula);

        User ala = new User(
                "Ala",
                "Kułakowska",
                "ala_kulak@gmail.com",
                passwordEncoder.encode("ala123"),
                Role.ROLE_USER);
        ala.getInstitutions().add(baber);
        userRepo.save(ala);

        Place place = new Place("stanowisko nr 1", baber);
        Place place2 = new Place("stanowisko nr 2", baber);
        placeRepo.save(place);
        placeRepo.save(place2);

        //====================

        Institution tatoo = new Institution();
        tatoo.setInstitutionName("tattoo");
        tatoo.setProvince("Dolnośląskie");
        tatoo.setCity("Wrocław");
        tatoo.setStreet("Swobodna");
        tatoo.setGateNumber("4");
        tatoo.setTypeOfService("Salon tatuażu");
        tatoo.setDescription("Dziary wszelakie");
        institutionRepo.save(tatoo);

        Representative ola = new Representative(
                "Ola",
                "Jajko",
                "ola_jajko@tatoo.com",
                passwordEncoder.encode("ola123"),
                Role.ROLE_USER);
        ola.getWorkPlaces().add(tatoo);
        representativeRepo.save(ola);

        Representative john = new Representative(
                "John",
                "Snith",
                "john@tatoo.com",
                passwordEncoder.encode("john123"),
                Role.ROLE_USER);
        john.getWorkPlaces().add(tatoo);
        representativeRepo.save(john);

        User janina = new User(
                "Janina",
                "Sobolewska",
                "janina@gmail.com",
                passwordEncoder.encode("janina123"),
                Role.ROLE_USER);
        janina.getInstitutions().add(tatoo);
        userRepo.save(janina);

        User jan = new User(
                "Jan",
                "Wojtaszko",
                "wojtaszkojan@gmail.com",
                passwordEncoder.encode("jan123"),
                Role.ROLE_USER);
        jan.getInstitutions().add(tatoo);
        jan.getInstitutions().add(baber);
        userRepo.save(jan);

        Place placeZ = new Place("leżąnka 1", tatoo);
        Place placeZ2 = new Place("leżąnka 2", tatoo);
        placeRepo.save(placeZ);
        placeRepo.save(placeZ2);
    }
}
