package com.zdzimi.registrationapp.comparator;

import com.zdzimi.registrationapp.model.entities.MonthTimetable;

import java.util.Comparator;

public class MonthTimetableComparator implements Comparator<MonthTimetable> {

    @Override
    public int compare(MonthTimetable o1, MonthTimetable o2) {
        int compare = Integer.compare(o1.getYear(), o2.getYear());
        if (compare == 0) {
            compare = Integer.compare(o1.getMonth(), o2.getMonth());
        }
        return compare;
    }
}
