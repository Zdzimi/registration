package com.zdzimi.registrationapp.validator;

import com.zdzimi.registrationapp.model.entities.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class DeletePlaceValidatorTest {

    @Parameterized.Parameter(0)
    public LocalTime visitTimeStart;
    @Parameterized.Parameter(1)
    public int dayOfMonth;
    @Parameterized.Parameter(2)
    public int year;
    @Parameterized.Parameter(3)
    public int month;
    @Parameterized.Parameter(4)
    public boolean result;

    @Parameterized.Parameters
    public static List data() {
        return Arrays.asList(new Object[][]{
            {LocalTime.now().plusMinutes(1), LocalDate.now().getDayOfMonth(), LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), false},
            {LocalTime.now().minusMinutes(1), LocalDate.now().getDayOfMonth(), LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), true}
        });
    }

    @Test
    public void test_parameterized() {
        Place place = new Place();
        Visit visit = new Visit();
        visit.setVisitTimeStart(visitTimeStart);
        DayTimetable dayTimetable = new DayTimetable();
        dayTimetable.setDayOfMonth(dayOfMonth);
        MonthTimetable monthTimetable = new MonthTimetable();
        monthTimetable.setYear(year);
        monthTimetable.setMonth(month);
        dayTimetable.setMonthTimetable(monthTimetable);
        visit.setDayTimetable(dayTimetable);
        place.setVisits(Set.of(visit));
        DeletePlaceValidator validator = new DeletePlaceValidator(place);
        assertEquals(result, validator.isValid());
    }
}