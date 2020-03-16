package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.comparator.UserComparator;
import com.zdzimi.registrationapp.exception.RepresentativeNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.repository.RepresentativeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepresentativeService {

    private RepresentativeRepo representativeRepo;

    @Autowired
    public RepresentativeService(RepresentativeRepo representativeRepo) {
        this.representativeRepo = representativeRepo;
    }

    public List<Representative> findRepresentativesFromInstitution(Institution institution) {
        return institution.getRepresentatives().stream()
                .sorted(new UserComparator())
                .collect(Collectors.toList());
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
