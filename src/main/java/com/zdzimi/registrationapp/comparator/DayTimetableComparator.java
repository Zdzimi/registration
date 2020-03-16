package com.zdzimi.registrationapp.comparator;

import com.zdzimi.registrationapp.model.entities.DayTimetable;

import java.util.Comparator;

public class DayTimetableComparator implements Comparator<DayTimetable> {

    @Override
    public int compare(DayTimetable o1, DayTimetable o2) {
        int compare = new MonthTimetableComparator().compare(o1.getMonthTimetable(), o2.getMonthTimetable());
        if (compare == 0) {
            compare = Integer.compare(o1.getDayOfMonth(), o2.getDayOfMonth());
        }
        return compare;
    }

}
