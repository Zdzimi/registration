package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.exception.RepresentativeNotFoundException;
import com.zdzimi.registrationapp.model.Institution;
import com.zdzimi.registrationapp.model.Representative;
import com.zdzimi.registrationapp.model.Role;
import com.zdzimi.registrationapp.repository.RepresentativeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RepresentativeService {

    private RepresentativeRepo representativeRepo;

    @Autowired
    public RepresentativeService(RepresentativeRepo representativeRepo) {
        this.representativeRepo = representativeRepo;
    }

    public Set<Representative> findRepresentatives(Institution institution) {
        return institution.getUsers().stream()
                .filter(user -> user.getRole() == Role.ROLE_ADMIN)
                .map(rep -> (Representative)rep)
                .collect(Collectors.toSet());
    }

    public Representative findRepresentativeByName(Institution institution, String representativeName) {
        return institution.getUsers().stream()
                .filter(user -> user.getRole() == Role.ROLE_ADMIN && user.getUsername().equals(representativeName))
                .map(user -> (Representative) user)
                .findAny().orElseThrow(() -> new RepresentativeNotFoundException(representativeName));
    }

    public Representative save(Representative representative) {
        return representativeRepo.save(representative);
    }
}
