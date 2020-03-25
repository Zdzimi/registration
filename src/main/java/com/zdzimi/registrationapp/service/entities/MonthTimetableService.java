package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.comparator.MonthTimetableComparator;
import com.zdzimi.registrationapp.exception.MonthTimetableNotFoundException;
import com.zdzimi.registrationapp.model.entities.Institution;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.repository.MonthTimetableRepo;
import com.zdzimi.registrationapp.validator.DeleteMonthTimetableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthTimetableService {

    private MonthTimetableRepo monthTimetableRepo;

    @Autowired
    public MonthTimetableService(MonthTimetableRepo monthTimetableRepo) {
        this.monthTimetableRepo = monthTimetableRepo;
    }

    public List<MonthTimetable> findByRepresentativeAndInstitution(Representative representative, Institution institution) {
        return monthTimetableRepo.findByRepresentativeAndInstitution(representative, institution);
    }

    public List<MonthTimetable> findByRepresentativeAndInstitutionAndYear(Representative representative,
                                                                          Institution institution, int year) {
        return monthTimetableRepo.findByRepresentativeAndInstitutionAndYear(representative, institution, year);
    }

    public MonthTimetable findByRepresentativeAndInstitutionAndYearAndMonth(Representative representative,
                                                                            Institution institution, int year, int month) {
        return monthTimetableRepo
                .findByRepresentativeAndInstitutionAndYearAndMonth(representative, institution, year, month)
                .orElseThrow(() -> new MonthTimetableNotFoundException(year, month));
    }

    public MonthTimetable findLastByRepresentativeAndInstitution(Representative representative, Institution institution) {
        return findByRepresentativeAndInstitution(representative, institution).stream()
                .max(new MonthTimetableComparator()).orElse(null);
    }

    public List<MonthTimetable> findActualByRepresentativeAndInstitution(Representative representative,
                                                                         Institution institution) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        return findByRepresentativeAndInstitution(representative, institution).stream()
                .filter(m -> m.getYear() >= year)
                .dropWhile(m -> m.getYear() == year && m.getMonth() < month)
                .collect(Collectors.toList());
    }

    public MonthTimetable findActualByRepresentativeInstitutionAndYearMonth(Representative representative,
                                                                            Institution institution,
                                                                            String yearMonth) {
        String[] split = yearMonth.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        return findActualByRepresentativeAndInstitution(representative, institution).stream()
                .filter(m -> m.getYear() == year && m.getMonth() == month)
                .findFirst().orElseThrow(() -> new MonthTimetableNotFoundException(year, month));
    }

    public void save(MonthTimetable monthTimetable) {
        monthTimetableRepo.save(monthTimetable);
    }

    public void deleteByRepresentativeAndInstitutionAndYearAndMonth(Representative representative, Institution institution,
                                                                    int year, int month) {
        MonthTimetable monthTimetable = findByRepresentativeAndInstitutionAndYearAndMonth(representative, institution, year, month);
        DeleteMonthTimetableValidator deleteMonthTimetableValidator = new DeleteMonthTimetableValidator(monthTimetable);
        if (deleteMonthTimetableValidator.isValid()) {        //      todo
            monthTimetableRepo.delete(monthTimetable);
        }
    }
}
