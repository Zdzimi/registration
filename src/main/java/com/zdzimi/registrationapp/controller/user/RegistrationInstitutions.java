package com.zdzimi.registrationapp.controller.user;

import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.service.entities.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("registration/{username}/institutions")
public class RegistrationInstitutions {

    private InstitutionService institutionService;

    @Autowired
    public RegistrationInstitutions(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping
    public List<Institution> showAllInstitutions() {
        return institutionService.findAll();
    }

    @GetMapping("/{institutionName}")
    public Institution showInstitution(@PathVariable String institutionName) {
        return institutionService.findByInstitutionName(institutionName);
    }
}
