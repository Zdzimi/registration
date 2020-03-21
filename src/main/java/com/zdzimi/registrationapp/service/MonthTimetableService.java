package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.comparator.MonthTimetableComparator;
import com.zdzimi.registrationapp.exception.MonthTimetableNotFoundException;
import com.zdzimi.registrationapp.model.MonthTimetableAndErrors;
import com.zdzimi.registrationapp.model.template.Template;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.model.template.YearAndMonth;
import com.zdzimi.registrationapp.repository.MonthTimetableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonthTimetableService {

    private MonthTimetableRepo monthTimetableRepo;
    private DayTimetableService dayTimetableService;

    @Autowired
    public MonthTimetableService(MonthTimetableRepo monthTimetableRepo, DayTimetableService dayTimetableService) {
        this.monthTimetableRepo = monthTimetableRepo;
        this.dayTimetableService = dayTimetableService;
    }

    public List<MonthTimetable> findAllByRepresentativeAndInstitution(Representative representative,
                                                                      Institution institution) {
        return representative.getMonthTimetables().stream()
                .filter(monthTimetable -> monthTimetable.getInstitution().equals(institution))
                .sorted(new MonthTimetableComparator())
                .collect(Collectors.toList());
    }

    public List<MonthTimetable> findByRepresentativeInstitutionAndYear(Representative representative,
                                                                       Institution institution,
                                                                       int year) {
        return representative.getMonthTimetables().stream()
                .filter(monthTimetable -> monthTimetable.getInstitution().equals(institution))
                .filter(m -> m.getYear() == year)
                .sorted(new MonthTimetableComparator())
                .collect(Collectors.toList());
    }

    public MonthTimetable findByRepresentativeInstitutionYearAndMonth(Representative representative,
                                                                      Institution institution,
                                                                      int year,
                                                                      int month) {
        return representative.getMonthTimetables().stream()
                .filter(monthTimetable -> monthTimetable.getInstitution().equals(institution))
                .filter(m -> m.getYear() == year && m.getMonth() == month)
                .findAny().orElseThrow(() -> new MonthTimetableNotFoundException(year, month));
    }

    public YearAndMonth findLastMonthTimetableByRepresentativeAneInstitution(Representative representative,
                                                                             Institution institution) {
        Optional<MonthTimetable> monthTimetable = representative.getMonthTimetables().stream()
                .filter(m -> m.getInstitution().equals(institution))
                .max(new MonthTimetableComparator());
        if (monthTimetable.isPresent()){
            int year = monthTimetable.get().getYear();
            int month = monthTimetable.get().getMonth();
            return new YearAndMonth(year, month);
        }
        return null;
    }

    public MonthTimetableAndErrors createAndSaveMonthTimetableFromTemplate(Institution institution,
                                                                           Representative representative,
                                                                           Template template) {
        MonthTimetable monthTimetable = createNewMonthTimetable(institution, representative, template);
        save(monthTimetable);
        return dayTimetableService.createAndSaveDayTimetables(monthTimetable, template, institution);
    }

    public void save(MonthTimetable monthTimetable) {
        monthTimetableRepo.save(monthTimetable);
    }

    private MonthTimetable createNewMonthTimetable(Institution institution,
                                                   Representative representative,
                                                   Template template) {
        int year = template.getYearAndMonth().getYear();
        int month = template.getYearAndMonth().getMonth();
        return new MonthTimetable(year, month, representative, institution);
    }

    public List<MonthTimetable> findActualByRepresentativeAndInstitution(Representative representative,
                                                                         Institution institution) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        return representative.getMonthTimetables().stream()
                .filter(monthTimetable -> monthTimetable.getInstitution().equals(institution))
                .filter(m -> m.getYear() >= year)
                .dropWhile(m -> m.getYear() == year && m.getMonth() < month)
                .collect(Collectors.toList());
    }

    public MonthTimetable findActualByRepresentativeInstitutionAndId(Representative representative,
                                                                     Institution institution,
                                                                     String yearMonth) {
        String[] split = yearMonth.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        return findActualByRepresentativeAndInstitution(representative, institution).stream()
                .filter(m -> m.getYear() == year && m.getMonth() == month)
                .findFirst().orElseThrow(() -> new MonthTimetableNotFoundException(year, month));
    }
}
