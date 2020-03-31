package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.InstitutionNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.repository.InstitutionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionService {

    private InstitutionRepo institutionRepo;

    @Autowired
    public InstitutionService(InstitutionRepo institutionRepo) {
        this.institutionRepo = institutionRepo;
    }

    public List<Institution> findAll() {
        return institutionRepo.findAll();
    }

    public Institution findByInstitutionName(String institutionName){
        return institutionRepo.findByInstitutionName(institutionName)
                .orElseThrow(() -> new InstitutionNotFoundException(institutionName));
    }

    public Institution save(Institution institution) {
        return institutionRepo.save(institution);
    }

    public List<Institution> findByUsers(User user) {
        return institutionRepo.findByUsers(user);
    }
}
