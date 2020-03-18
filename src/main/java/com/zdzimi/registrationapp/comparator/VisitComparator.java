package com.zdzimi.registrationapp.comparator;

import com.zdzimi.registrationapp.model.entities.Visit;

import java.util.Comparator;

public class VisitComparator implements Comparator<Visit> {

    @Override
    public int compare(Visit o1, Visit o2) {
        int compare = new MonthTimetableComparator()
                .compare(o1.getDayTimetable().getMonthTimetable(), o2.getDayTimetable().getMonthTimetable());
        if (compare == 0) {
            compare = new DayTimetableComparator()
                    .compare(o1.getDayTimetable(), o2.getDayTimetable());
            if (compare == 0) {
                compare = o1.getVisitTimeStart().compareTo(o2.getVisitTimeStart());
            }
        }
        return compare;
    }
}
