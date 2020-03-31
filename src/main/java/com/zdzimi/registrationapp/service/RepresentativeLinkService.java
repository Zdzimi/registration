package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.controller.representative.RegistrationAppController;
import com.zdzimi.registrationapp.model.MonthTimetableAndErrors;
import com.zdzimi.registrationapp.model.entities.*;
import com.zdzimi.registrationapp.model.template.Template;
import com.zdzimi.registrationapp.service.entities.DayTimetableService;
import com.zdzimi.registrationapp.service.entities.VisitService;
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

    @Autowired
    public RepresentativeLinkService(DayTimetableService dayTimetableService, VisitService visitService) {
        this.dayTimetableService = dayTimetableService;
        this.visitService = visitService;
    }

    public void addLinkToInstitution(Institution institution, String institutionName) {
        institution.add(getLinkToAllRepresentatives(institutionName));
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
                getLinkToInstitution(institutionName)
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
        links.add(getLinkToInstitution(institutionName));
        links.add(getLinkToRepresentative(institutionName, representativeName));
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
        links.add(getLinkToInstitution(institutionName));
        links.add(getLinkToRepresentative(institutionName, representativeName));
        links.add(getLinkToMonthTimetable(institutionName, representativeName, year, month));
        dayTimetable.add(links);
    }

    public void addBackLinks(Visit visit, String institutionName, String representativeName, int year, int month, int day) {
        visit.add(
                getLinkToInstitution(institutionName),
                getLinkToRepresentative(institutionName, representativeName),
                getLinkToMonthTimetable(institutionName, representativeName, year, month),
                getLinkToDayTimetable(institutionName, representativeName, year, month, day)
                );
    }

    public void addLinksToNextTemplate(Template template, String institutionName, String representativeName) {
        template.add(
                getLinkToInstitution(institutionName),
                getLinkToRepresentative(institutionName, representativeName)
        );
    }

    public void addLinksToNextTimetableAndErrors(MonthTimetableAndErrors timetableAndErrors,
                                                 String institutionName, String representativeName) {
        timetableAndErrors.add(
                getLinkToInstitution(institutionName),
                getLinkToRepresentative(institutionName, representativeName)
        );
    }

    public void addLinksToMonthTemplate(Template template, String institutionName,
                                        String representativeName, int year, int month) {
        template.add(
                getLinkToInstitution(institutionName),
                getLinkToRepresentative(institutionName, representativeName),
                getLinkToMonthTimetable(institutionName, representativeName, year, month)
        );
    }

    public void addLinksToMonthTimetableAndErrors(Template template, String institutionName,
                                                  String representativeName, int year, int month) {
        template.add(
                getLinkToInstitution(institutionName),
                getLinkToRepresentative(institutionName, representativeName),
                getLinkToMonthTimetable(institutionName, representativeName, year, month)
        );
    }

    public void addLinksToDayTemplate(Template template, String institutionName,
                                      String representativeName, int year, int month, int day) {
        template.add(
                getLinkToInstitution(institutionName),
                getLinkToRepresentative(institutionName, representativeName),
                getLinkToMonthTimetable(institutionName, representativeName, year, month),
                getLinkToDayTimetable(institutionName, representativeName, year, month, day)
        );
    }

    public void addLinksToDayTimetableAndErrors(MonthTimetableAndErrors timetableAndErrors, String institutionName,
                                                String representativeName, int year, int month, int day) {
        timetableAndErrors.add(
                getLinkToInstitution(institutionName),
                getLinkToRepresentative(institutionName, representativeName),
                getLinkToMonthTimetable(institutionName, representativeName, year, month),
                getLinkToDayTimetable(institutionName, representativeName, year, month, day)
        );
    }

    private Link getLinkToInstitution(String institutionName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .withRel(institutionName);
    }

    private Link getLinkToAllRepresentatives(String institutionName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representative")
                .withRel("representatives");
    }

    private Link getLinkToRepresentative(String institutionName, String representativeName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representative")
                .slash(representativeName)
                .withRel(representativeName);
    }

    private Link getLinkToMonthTimetables(String representativeName, String institutionName) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representative")
                .slash(representativeName)
                .slash("timetables")
                .withRel("timetables");
    }

    private Link getLinkToMonthTimetableNextTemplate(String representativeName, String institutionName) {
        return linkTo(RegistrationAppController.class).slash(institutionName)
                .slash("representative")
                .slash(representativeName)
                .slash("timetables")
                .slash("get-next-template")
                .withRel("get-next-template");
    }

    private Link getLinkToMonthTimetable(String institutionName, String representativeName, int year, int month) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representative")
                .slash(representativeName)
                .slash("timetables")
                .slash(year)
                .slash(month)
                .withRel(year + "-" + month);
    }

    private Link getLinkToDayTimetable(String institutionName, String representativeName, int year, int month, int day) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representative")
                .slash(representativeName)
                .slash("timetables")
                .slash(year)
                .slash(month)
                .slash(day)
                .withRel(year + "-" + month + "-" + day);
    }

    private Link getLinkToMonthTemplate(String institutionName, String representativeName, int year, int month) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representative")
                .slash(representativeName)
                .slash("timetables")
                .slash(year)
                .slash(month)
                .slash("get-template")
                .withRel("get-template");
    }

    private Link getLinkToDayTemplate(String institutionName, String representativeName, int year, int month, int day) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representative")
                .slash(representativeName)
                .slash("timetables")
                .slash(year)
                .slash(month)
                .slash(day)
                .slash("get-template")
                .withRel("get-template");
    }

    private Link getLinkToVisit(String institutionName, String representativeName,
                                int year, int month, int day, Visit visit) {
        return linkTo(RegistrationAppController.class)
                .slash(institutionName)
                .slash("representative")
                .slash(representativeName)
                .slash("timetables")
                .slash(year)
                .slash(month)
                .slash(day)
                .slash(visit.getVisitId())
                .withRel(year + "." + month + "." + day + " " + visit.getVisitTimeStart() +
                        " - " + visit.getVisitTimeStart().plusMinutes(visit.getVisitTimeLength()));
    }
}
