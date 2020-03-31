package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.controller.user.RegistrationController;
import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.service.entities.DayTimetableService;
import com.zdzimi.registrationapp.service.entities.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class UserLinkService {

    private DayTimetableService dayTimetableService;
    private VisitService visitService;

    @Autowired
    public UserLinkService(DayTimetableService dayTimetableService, VisitService visitService) {
        this.dayTimetableService = dayTimetableService;
        this.visitService = visitService;
    }

    public void addLinks(User user) {
        user.add(getLinkToAllInstitutions(user.getUsername()),
                getLinkToMyInstitutions(user.getUsername()),
                getLinkToVisits(user.getUsername()));
    }

    public void addLinksToInstitutions(List<Institution> institutionList, String username) {
        for (Institution institution : institutionList) {
            institution.add(getLinkToInstitution(username, institution.getInstitutionName()));
        }
    }

    public void addLinksToInstitution(Institution institution, String username) {
            institution.add(getLinkToRepresentatives(username, institution.getInstitutionName()), getLinkToUser(username));
    }

    public void addLinksToRepresentatives(List<Representative> representativeList,
                                          String username, String institutionName) {
        for (Representative representative : representativeList) {
            representative.add(getLinkToRepresentative(username, institutionName, representative.getUsername()));
        }
    }

    public void addLinksToRepresentative(Representative representative, String username, String institutionName) {
        representative.add(
                getLinkToMonthTimetables(username, institutionName, representative.getUsername()),
                getLinkToUser(username),
                getLinkToRepresentatives(username, institutionName));
    }

    public void addLinksToMonthTimetables(List<MonthTimetable> monthTimetableList, String username,
                                          String institutionName, String representativeName) {
        for (MonthTimetable monthTimetable : monthTimetableList) {
            String yearMonth = monthTimetable.getYear() + "-" + monthTimetable.getMonth();
            monthTimetable.add(getLinkToMonthTimetable(username, institutionName, representativeName, yearMonth));
        }
    }

    public void addLinksToMonthTimetable(MonthTimetable monthTimetable, String username,
                                         String institutionName, String representativeName, String yearMonth) {
        List<Link> links = new ArrayList<>();
        List<DayTimetable> dayTimetableList = dayTimetableService.findByMonthTimetable(monthTimetable);
        for (DayTimetable dayTimetable : dayTimetableList) {
            links.add(getLinkToDayTimetable(username,institutionName,representativeName,yearMonth,dayTimetable.getDayOfMonth()));
        }
        monthTimetable.add(links);
        monthTimetable.add(getLinkToUser(username), getLinkToRepresentatives(username, institutionName));
    }

    public void addLinksToDayTimetable(DayTimetable dayTimetable, String username, String institutionName,
                                       String representativeName, String yearMonth, int day) {
        List<Visit> visitList = visitService.findByDayTimetable(dayTimetable);
        List<Link> links = new ArrayList<>();
        for (Visit visit : visitList) {
            links.add(getLinkToVisit(username,institutionName,representativeName,yearMonth,day,visit));
        }
        dayTimetable.add(links);
        dayTimetable.add(
                getLinkToUser(username),
                getLinkToRepresentatives(username, institutionName),
                getLinkToMonthTimetable(username, institutionName, representativeName, yearMonth)
        );
    }

    public void addLinksToVisit(Visit visit, String username, String institutionName,
                                String representativeName, String yearMonth, int day, long visitId) {
        visit.add(getLinkToBookVisit(
                username, institutionName, representativeName, yearMonth, day, visitId),
                getLinkToUser(username),
                getLinkToRepresentatives(username, institutionName),
                getLinkToMonthTimetable(username, institutionName, representativeName, yearMonth),
                getLinkToDayTimetable(username, institutionName, representativeName,yearMonth, day)
        );
    }

    public void addLinkToBack(Visit visit, String username) {
        visit.add(getLinkToUser(username));
    }

    public void addLinksToBack(Visit bookVisit, String username, String institutionName,
                               String representativeName, String yearMonth, int day) {
        bookVisit.add(
                getLinkToUser(username),
                getLinkToRepresentatives(username, institutionName),
                getLinkToMonthTimetable(username, institutionName, representativeName, yearMonth),
                getLinkToDayTimetable(username, institutionName, representativeName,yearMonth, day)
        );
    }

    public void addLinksToMyVisits(List<Visit> visitList, String username) {
        for (Visit visit : visitList) {
            visit.add(getLinkToMyVisit(username, visit.getVisitId()));
        }
    }

    private Link getLinkToMyVisit(String username, long visitId) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("visits")
                .slash(visitId)
                .withRel("visit no: " + visitId);
    }

    private Link getLinkToBookVisit(String username, String institutionName,
                                    String representativeName, String yearMonth, int day, long visitId) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash(yearMonth)
                .slash(day)
                .slash(visitId)
                .slash("book")
                .withRel("book");
    }

    private Link getLinkToVisit(String username, String institutionName,
                                String representativeName, String yearMonth, int day, Visit visit) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash(yearMonth)
                .slash(day)
                .slash(visit.getVisitId())
                .withRel(yearMonth + "-" + day + " - " +
                        visit.getVisitTimeStart() + " - " +
                        visit.getVisitTimeStart().plusMinutes(visit.getVisitTimeLength()));
    }

    private Link getLinkToDayTimetable(String username, String institutionName,
                                       String representativeName, String yearMonth, int dayOfMonth) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash(yearMonth)
                .slash(dayOfMonth)
                .withRel(yearMonth + "-" + dayOfMonth);
    }

    private Link getLinkToMonthTimetable(String username, String institutionName,
                                         String representativeName, String yearMonth) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .slash(yearMonth)
                .withRel(yearMonth);
    }

    private Link getLinkToMonthTimetables(String username, String institutionName, String representativeName) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .slash("timetables")
                .withRel("timetables");
    }

    private Link getLinkToRepresentative(String username, String institutionName, String representativeName) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .slash(institutionName)
                .slash("representatives")
                .slash(representativeName)
                .withRel(representativeName);
    }

    private Link getLinkToRepresentatives(String username, String institutionName) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .slash(institutionName)
                .slash("representatives")
                .withRel("representatives");
    }

    private Link getLinkToInstitution(String username, String institutionName) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .slash(institutionName)
                .withRel(institutionName);
    }

    private Link getLinkToVisits(String username) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("visits")
                .withRel("visits");
    }

    private Link getLinkToMyInstitutions(String username) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .slash("know")
                .withRel("know");
    }

    private Link getLinkToAllInstitutions(String username) {
        return linkTo(RegistrationController.class)
                .slash(username)
                .slash("institutions")
                .withRel("institutions");
    }

    private Link getLinkToUser(String username) {
        return linkTo(RegistrationController.class).slash(username).withRel(username);
    }
}
