package com.zdzimi.registrationapp.validator;

import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.Place;
import com.zdzimi.registrationapp.model.entities.Visit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(Parameterized.class)
public class VisitValidatorTest {

    @Parameterized.Parameter(0)
    public Visit visit;
    @Parameterized.Parameter(1)
    public LocalTime timeStart;
    @Parameterized.Parameter(2)
    public long visitTime;
    @Parameterized.Parameter(3)
    public boolean result;

    @Parameterized.Parameters
    public static List data() {
        return Arrays.asList(new Object[][]{
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(10,30),
                        60,
                        true},
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(11,0),
                        60,
                        true},
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(11,30),
                        60,
                        false},
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(12,0),
                        60,
                        false},
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(12,30),
                        60,
                        false},
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(13,0),
                        60,
                        true},
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(13,10),
                        60,
                        true},
                {new Visit(LocalTime.of(12,0),20, new DayTimetable(), new Place()),
                        LocalTime.of(11,50),
                        60,
                        false},
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(12,20),
                        20,
                        false},
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(12,30),
                        60,
                        false},
                {new Visit(LocalTime.of(12,0),60, new DayTimetable(), new Place()),
                        LocalTime.of(11,30),
                        60,
                        false}
        });
    }

    @Test
    public void test_parameterized() {
        VisitValidator validator = new VisitValidator(Collections.singleton(visit), timeStart, visitTime);
        Assert.assertEquals(result, validator.isValid());
    }
}