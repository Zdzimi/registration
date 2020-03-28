package com.zdzimi.registrationapp.service.template;

import com.zdzimi.registrationapp.model.template.Day;
import com.zdzimi.registrationapp.model.template.Template;
import com.zdzimi.registrationapp.model.template.YearAndMonth;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateService {

    public Template getNextTemplate(YearAndMonth yearAndMonth) {
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

    public Template getDayTemplate(int year, int month, int dayOfMonth) {
        YearAndMonth yearAndMonth = new YearAndMonth(year, month);
        LocalDate date = LocalDate.of(year, month, 1);
        int length = Month.of(month).length(date.isLeapYear());
        List<Day> days = new ArrayList<>();
        if (dayOfMonth > 0 && dayOfMonth < length + 1) {
            days.add(new Day(dayOfMonth));
        }
        return new Template(yearAndMonth, days);
    }

    public Template getMonthTemplate(int year, int month) {
        return createTemplate(LocalDate.of(year,month,1));
    }
}
