package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.controller.representative.RegistrationAppController;
import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.model.template.Template;
import com.zdzimi.registrationapp.service.entities.DayTimetableService;
import com.zdzimi.registrationapp.service.entities.VisitService;
import com.zdzimi.registrationapp.validator.DeleteOrBookVisitValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class RepresentativeLinkService {

    private DayTimetableService dayTimetableService;
    private VisitService visitService;
    private UserLinkService userLinkService;

    @Autowired
    public RepresentativeLinkService(DayTimetableService dayTimetableService,
                                     VisitService visitService,
                                     UserLinkService userLinkService) {
        this.dayTimetableService = dayTimetableService;
        this.visitService = visitService;
        this.userLinkService = userLinkService;
    }

    public void addLinkToInstitution(Institution institution) {
        institution.add(
                getLinkToUsers(institution.getInstitutionName()),
                getLinkToAllRepresentatives(institution.getInstitutionName()),
                getLinkToPlaces(institution.getInstitutionName())
        );
    }

    public void addLinksToAllRepresentatives(List<Representative> representativeList, String institutionName) {
        for (Representative representative : representativeList) {
            representative.add(getLinkToRepresentative(institutionName, representative.getUsername()));
        }
    }

    public void addLinksRepresentative(Representative representative, String institutionName) {
        representative.add(
                getLinkToMonthTimetables(representative.getUsername(), institutionName),
                getLinkToMonthTimetableNextTemplate(representative.getUsername(), institutionName),
                getLinkToInstitution(institutionName),
                userLinkService.getLinkToUser(representative.getUsername())
        );
    }

    public void addLinksToAllMonthTimetables(List<MonthTimetable> monthTimetables,
                                             String institutionName, String representativeName) {
        for (MonthTimetable monthTimetable : monthTimetables) {
            monthTimetable.add(getLinkToMonthTimetable(institutionName, representativeName,
                    monthTimetable.getYear(), monthTimetable.getMonth()));
        }
    }

    public void addLinksToMonthTimetable(MonthTimetable monthTimetable, String institutionName,
                                         String representativeName, int year, int month) {
        List<Link> links = new ArrayList<>();
        List<DayTimetable> dayTimetableList = dayTimetableService.findByMonthTimetable(monthTimetable);
        for (DayTimetable dayTimetable : dayTimetableList) {
            links.add(getLinkToDayTimetable(institutionName,representativeName,year,month,dayTimetable.getDayOfMonth()));
        }
        links.add(getLinkToMonthTemplate(institutionName, representativeName, year, month));
        links.add(getLinkToRepresentative(institutionName, representativeName));
        links.add(getLinkToInstitution(institutionName));
        links.add(userLinkService.getLinkToUser(representativeName));
        monthTimetable.add(links);
    }

    public void addLinksToDayTimetable(DayTimetable dayTimetable, String institutionName,
                                       String representativeName, int year, int month, int day) {
        List<Link> links = new ArrayList<>();
        List<Visit> visitList = visitService.findByDayTimetable(dayTimetable);
        for (Visit visit : visitList) {
            links.add(getLinkToVisit(institutionName, representativeName, year, month, day, visit));
        }
        links.add(getLinkToDayTemplate(institutionName, representativeName, year, month, day));
        links.add(getLinkToMonthTimetable(institutionName, representativeName, year, month));
        links.add(getLinkToRepresentative(institutionName, representativeName));
        links.add(getLinkToInstitution(institutionName));
        links.add(userLinkService.getLinkToUser(representativeName));
        dayTimetable.add(links);
    }

    public void addLinksToNextTemplate(Template template, String institutionName, String representativeName) {
        template.add(
                getLinkToMonthTimetableNextTemplate(representativeName, institutionName),
                getLinkToRepresentative(institutionName, representativeName),
                getLinkToInstitution(institutionName),
                userLinkService.getLinkToUser(representativeName)
        );
    }

    public void addBackLinks(Visit visit, String institutionName, String representativeName, int year, int month, int day) {
        if (new DeleteOrBookVisitValidator(visit).isValid()) {
            visit.add(getLinkToDeleteVisit(institutionName, representativeName, year, month, day, visit));
        }
        visit.add(
                getLinkBackToDayTimetable(institutionName, representativeName, year, month, day),
                getLinkToMonthTimetable(institutionName, representativeName, year, month),
                getLinkToRepresentative(institutionName, representativeName),
                getLinkToInstitution(institutionName),
                userLinkService.getLinkToUser(representativeName)
        );
    }

    public void addLinksToMonthTemplate(Template template, String institutionName,
                                        String representativeName, int year, int month) {
        template.add(
                getLinkToMonthTemplate(institutionName, representativeName, year, month),
                getLinkToMonthTimetable(institutionName, representativeName, year, month),
                getLinkToRepresentative(institutionName, representativeName),
                getLinkToInstitution(institutionName),
                userLinkService.getLinkToUser(representativeName)
        );
    }

    public void addLinksToDayTemplate(Template template, String institutionName,
                                      String representativeName, int year, int month, int day) {
        template.add(
                getLinkToDayTemplate(institutionName, representativeName, year, month, day),
                getLinkBackToDayTimetable(institutionName, representativeName, year, month, day),
                getLinkToMonthTimetable(institutionName, representativeName, year, month),
                getLinkToRepresentative(institutionName, representativeName),
                getLinkToInstitution(institutionName),
                userLinkService.getLinkToUser(representativeName)
        );
    }

    public void addLinksToUsers(List<User> userList, String institutionName) {
        for (User user : userList) {
            user.add(getLinkToUser(institutionName, user.getUsername()));
        }
    }

    public void addLinksToUsersVisits(User user, Institution institution) {
        List<Visit> visits = visitService.findByUserAndInstitution(user, institution);
        for (Visit visit : visits) {
            user.add(getLinkToUsersVisit(institution.getInstitutionName(), user.getUsername(), visit));
        }
        user.add(getLinkToInstitution(institution.getInstitutionName()));
    }

    public void addLinksToUsersVisit(Visit visit, Institution institution) {
        visit.add(getLinkToInstitution(institution.getInstitutionName()));
    }

    public void addLinksToPlaces(List<Place> places, String institutionName) {
        for (Place place : places) {
            place.add(getLinkToPlace(place, institutionName));
        }
    }

    public void addLinksToPlace(Place place, String institutionName) {
        place.add(
                getLinkToPlace(place, institutionName),
                getLinkToInstitution(institutionName)
        );
    }

    private Link getLinkBackToDayTimetable(String institutionName, String representativeName, int year, int month, int day) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash("y")
                .slash(year)
                .slash("m")
                .slash(month)
                .slash("d")
                .slash(day)
                .withRel(String.format("%d.%s.%s",
                        year,
                        month < 10 ? String.format("0%d", month) : month,
                        day < 10 ? String.format("0%d", day) : day));
    }

    private Link getLinkToDeleteVisit(String institutionName, String representativeName, int year, int month, int day, Visit visit) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash("y")
                .slash(year)
                .slash("m")
                .slash(month)
                .slash("d")
                .slash(day)
                .slash("v")
                .slash(visit.getVisitId())
                .withRel("usuń wizytę");
    }

    private Link getLinkToInstitution(String institutionName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .withRel(institutionName);
    }

    private Link getLinkToAllRepresentatives(String institutionName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .withRel("pracownicy " + institutionName);
    }

    private Link getLinkToRepresentative(String institutionName, String representativeName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .withRel(institutionName + " - " + representativeName);
    }

    private Link getLinkToMonthTimetables(String representativeName, String institutionName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .withRel("kalendarz");
    }

    private Link getLinkToMonthTimetableNextTemplate(String representativeName, String institutionName) {
        return linkTo(RegistrationAppController.class).slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash("get-next-template")
                .withRel("następny szablon");
    }

    private Link getLinkToMonthTimetable(String institutionName, String representativeName, int year, int month) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash("y")
                .slash(year)
                .slash("m")
                .slash(month)
                .withRel(String.format("%d.%s",
                        year,
                        month < 10 ? "0" + month : month
                ));
    }

    private Link getLinkToDayTimetable(String institutionName, String representativeName, int year, int month, int day) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash("y")
                .slash(year)
                .slash("m")
                .slash(month)
                .slash("d")
                .slash(day)
                .withRel("" + day);
    }

    private Link getLinkToMonthTemplate(String institutionName, String representativeName, int year, int month) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash("y")
                .slash(year)
                .slash("m")
                .slash(month)
                .slash("get-template")
                .withRel(String.format("%s.%d - szablon", month < 10 ? String.format("0%d", month) : month, year));
    }

    private Link getLinkToDayTemplate(String institutionName, String representativeName, int year, int month, int day) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash("y")
                .slash(year)
                .slash("m")
                .slash(month)
                .slash("d")
                .slash(day)
                .slash("get-template")
                .withRel(String.format("%s.%s.%dr. - szablon",
                        day < 10 ? String.format("0%d", day) : day,
                        month < 10 ? String.format("0%d", month) : month,
                        year));
    }

    private Link getLinkToVisit(String institutionName, String representativeName,
                                int year, int month, int day, Visit visit) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash("y")
                .slash(year)
                .slash("m")
                .slash(month)
                .slash("d")
                .slash(day)
                .slash("v")
                .slash(visit.getVisitId())
                .withRel(String.format(
                        "%s - %s",
                        visit.getVisitTimeStart(),
                        visit.getVisitTimeStart().plusMinutes(visit.getVisitTimeLength())
                ));
    }

    private Link getLinkToUser(String institutionName, String username) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("users")
                .slash(username)
                .withRel(username);
    }

    private Link getLinkToUsersVisit(String institutionName, String username, Visit visit) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("users")
                .slash(username)
                .slash(visit.getVisitId())
                .withRel(String.format(
                        "%s - %s - %s.%s.%dr.",
                        username,
                        visit.getVisitTimeStart(),
                        visit.getDayTimetable().getDayOfMonth() < 10
                                ? String.format("0%d", visit.getDayTimetable().getDayOfMonth())
                                : visit.getDayTimetable().getDayOfMonth(),
                        visit.getDayTimetable().getMonthTimetable().getMonth() < 10
                                ? String.format("0%d", visit.getDayTimetable().getMonthTimetable().getMonth())
                                : visit.getDayTimetable().getMonthTimetable().getMonth(),
                        visit.getDayTimetable().getMonthTimetable().getYear())
                );
    }

    private Link getLinkToPlace(Place place, String institutionName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("places")
                .slash(place.getPlaceName())
                .withRel(place.getPlaceName());
    }

    private Link getLinkToPlaces(String institutionName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("places")
                .withRel("stanowiska");
    }

    private Link getLinkToUsers(String institutionName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("users")
                .withRel("klienci " + institutionName);
    }
}
