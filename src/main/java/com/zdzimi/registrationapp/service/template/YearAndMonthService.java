package com.zdzimi.registrationapp.service.template;

import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.template.YearAndMonth;
import org.springframework.stereotype.Service;

@Service
public class YearAndMonthService {

    public YearAndMonth getYearAndMonth(MonthTimetable monthTimetable) {
        if (monthTimetable != null) {
            int year = monthTimetable.getYear();
            int month = monthTimetable.getMonth();
            return new YearAndMonth(year, month);
        }
        return null;
    }
}
