package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.exception.InstitutionNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.repository.InstitutonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {

    private InstitutonRepo institutonRepo;

    @Autowired
    public InstitutionService(InstitutonRepo institutonRepo) {
        this.institutonRepo = institutonRepo;
    }

    public List<Institution> findAll() {
        return institutonRepo.findAll();
    }

    public Institution findInstitution(String institutionName){
        return institutonRepo.findByInstitutionName(institutionName)
                .orElseThrow(() -> new InstitutionNotFoundException(institutionName));
    }

    public Institution save(Institution institution) {
        return institutonRepo.save(institution);
    }
}
