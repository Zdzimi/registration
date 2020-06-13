package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.comparator.DayTimetableComparator;
import com.zdzimi.registrationapp.exception.DayTimetableNotFoundException;
import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.repository.DayTimetableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DayTimetableService {

    private DayTimetableRepo dayTimetableRepo;

    @Autowired
    public DayTimetableService(DayTimetableRepo dayTimetableRepo) {
        this.dayTimetableRepo = dayTimetableRepo;
    }

    public List<DayTimetable> findByMonthTimetable(MonthTimetable monthTimetable) {
        return dayTimetableRepo.findByMonthTimetable(monthTimetable).stream()
                .sorted(new DayTimetableComparator())
                .collect(Collectors.toList());
    }

    public DayTimetable findByMonthTimetableAndDayOfMonth(MonthTimetable monthTimetable, int day) {
        return dayTimetableRepo.findByMonthTimetableAndDayOfMonth(monthTimetable, day)
                .orElseThrow(() -> new DayTimetableNotFoundException(monthTimetable.getYear(), monthTimetable.getMonth(), day));
    }

    public DayTimetable getOrCreate(int dayNumber, MonthTimetable monthTimetable) {
        DayTimetable dayTimetable = dayTimetableRepo
                .findByMonthTimetableAndDayOfMonth(monthTimetable, dayNumber)
                .orElse(null);
        if (dayTimetable == null) {
            dayTimetable = new DayTimetable(dayNumber, monthTimetable);
            save(dayTimetable);
        }
        return dayTimetable;
    }

    public void save(DayTimetable dayTimetable) {
        dayTimetableRepo.save(dayTimetable);
    }
}
