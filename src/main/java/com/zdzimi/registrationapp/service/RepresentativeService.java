package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.exception.RepresentativeNotFoundException;
import com.zdzimi.registrationapp.model.Institution;
import com.zdzimi.registrationapp.model.Representative;
import com.zdzimi.registrationapp.repository.RepresentativeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RepresentativeService {

    private RepresentativeRepo representativeRepo;

    @Autowired
    public RepresentativeService(RepresentativeRepo representativeRepo) {
        this.representativeRepo = representativeRepo;
    }

    public Set<Representative> findRepresentativesFromInstitution(Institution institution) {
        return institution.getRepresentatives();
    }

    public Representative findRepresentativeFromInstitutionByName(Institution institution, String representativeName) {
        return institution.getRepresentatives().stream()
                .filter(representative -> representative.getUsername().equals(representativeName))
                .findAny().orElseThrow(()-> new RepresentativeNotFoundException(representativeName));
    }

    public List<Representative> findAllRepresentatives(){
        return representativeRepo.findAll();
    }

    public Representative save(Representative representative) {
        return representativeRepo.save(representative);
    }
}
