package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.RepresentativeNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.repository.RepresentativeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentativeService {

    private RepresentativeRepo representativeRepo;

    @Autowired
    public RepresentativeService(RepresentativeRepo representativeRepo) {
        this.representativeRepo = representativeRepo;
    }

    public List<Representative> findAll(){
        return representativeRepo.findAll();
    }

    public List<Representative> findByWorkPlaces(Institution institution) {
        return representativeRepo.findByWorkPlaces(institution);
    }

    public Representative findByWorkPlacesAndUsername(Institution institution, String representativeName) {
        return representativeRepo.findByWorkPlacesAndUsername(institution, representativeName)
                .orElseThrow(() -> new RepresentativeNotFoundException(representativeName));
    }

    public Representative save(Representative representative) {
        return representativeRepo.save(representative);
    }
}
