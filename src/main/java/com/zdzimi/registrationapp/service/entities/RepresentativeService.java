package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.exception.RepresentativeNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.model.entities.Visit;
import com.zdzimi.registrationapp.repository.RepresentativeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RepresentativeService {

    private RepresentativeRepo representativeRepo;
    private UserService userService;
    private InstitutionService institutionService;
    private VisitService visitService;

    @Autowired
    public RepresentativeService(RepresentativeRepo representativeRepo,
                                 UserService userService,
                                 InstitutionService institutionService,
                                 VisitService visitService) {
        this.representativeRepo = representativeRepo;
        this.userService = userService;
        this.institutionService = institutionService;
        this.visitService = visitService;
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

    public Representative createRepresentativeFromUser(User user) {
        Representative representative = getRepresentative(user);

        Set<Institution> institutions = user.getInstitutions();
        Set<Visit> visits = user.getVisits();

        removeUserFromInstitutions(user, institutions);
        removeUserFromVisits(visits);

        userService.delete(user);
        representativeRepo.save(representative);

        setRepresentativeInVisits(representative, visits);
        return representative;
    }

    private void setRepresentativeInVisits(Representative representative, Set<Visit> visits) {
        for (Visit visit : visits) {
            visit.setUser(representative);
            visitService.save(visit);
        }
    }

    private void removeUserFromVisits(Set<Visit> visits) {
        setRepresentativeInVisits(null, visits);
    }

    private void removeUserFromInstitutions(User user, Set<Institution> institutions) {
        for (Institution institution : institutions) {
            institution.getUsers().remove(user);
            institutionService.save(institution);
        }
    }

    private Representative getRepresentative(User user) {
        Representative representative = new Representative();
        representative.setUsername(user.getUsername());
        representative.setName(user.getName());
        representative.setSurname(user.getSurname());
        representative.setEmail(user.getEmail());
        representative.setPassword(user.getPassword());
        representative.setRole(user.getRole());
        representative.setInstitutions(user.getInstitutions());
        representative.setVisits(user.getVisits());
        return representative;
    }

    public Representative getRepresentativeByUser(User user) {
        if (user instanceof Representative) {
            return ((Representative) user);
        } else {
            return createRepresentativeFromUser(user);
        }
    }
}
