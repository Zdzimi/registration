package com.zdzimi.registrationapp.service;

import com.zdzimi.registrationapp.model.Day;
import com.zdzimi.registrationapp.model.Template;
import com.zdzimi.registrationapp.model.YearAndMonth;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateService {

    public Template getNextEmptyTemloate(YearAndMonth yearAndMonth) {
        LocalDate date = LocalDate.now();
        if(yearAndMonth != null){
            LocalDate nextDate = LocalDate.of(yearAndMonth.getYear(),yearAndMonth.getMonth(),1).plusMonths(1);
            date = date.isBefore(nextDate) ? nextDate : date;
        }
        return createTemplate(date);
    }

    private Template createTemplate(LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();
        int length = date.getMonth().length(date.isLeapYear());
        YearAndMonth yearAndMonth = new YearAndMonth(date.getYear(), date.getMonthValue());
        List<Day> days = new ArrayList<>();
        for (int i = dayOfMonth; i < length + 1; i++) {
            days.add(new Day(i));
        }
        return new Template(yearAndMonth, days);
    }
}
