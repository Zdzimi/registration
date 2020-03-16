package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.comparator.MonthTimetableComparator;
import com.zdzimi.registrationapp.exception.MonthTimetableNotFoundException;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.entities.Representative;
import com.zdzimi.registrationapp.model.YearAndMonth;
import com.zdzimi.registrationapp.repository.MonthTimetableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonthTimetableService {

    private MonthTimetableRepo monthTimetableRepo;

    @Autowired
    public MonthTimetableService(MonthTimetableRepo monthTimetableRepo) {
        this.monthTimetableRepo = monthTimetableRepo;
    }

    public List<MonthTimetable> findAllMonthTimetablesByRepresentative(Representative representative) {
        return representative.getMonthTimetables().stream()
                .sorted(new MonthTimetableComparator())
                .collect(Collectors.toList());
    }

    public List<MonthTimetable> findMonthTimetablesByRepresentativeAndYear(Representative representative,
                                                                          int year) {
        return representative.getMonthTimetables().stream()
                .filter(m -> m.getYear() == year)
                .sorted(new MonthTimetableComparator())
                .collect(Collectors.toList());
    }

    public MonthTimetable findMonthTimetablesByYearAndMonthAndRepresentative(Representative representative,
                                                                                  int year,
                                                                                  int month) {
        return representative.getMonthTimetables().stream()
                .filter(m -> m.getYear() == year && m.getMonth() == month)
                .findAny().orElseThrow(() -> new MonthTimetableNotFoundException(year, month));
    }

    public YearAndMonth findLastMonthTimetable(Representative representative) {
        Optional<MonthTimetable> monthTimetable = representative.getMonthTimetables().stream()
                .max(new MonthTimetableComparator());        //      todo???
        if (monthTimetable.isPresent()){
            int year = monthTimetable.get().getYear();
            int month = monthTimetable.get().getMonth();
            return new YearAndMonth(year, month);
        }
        return null;
    }
}
