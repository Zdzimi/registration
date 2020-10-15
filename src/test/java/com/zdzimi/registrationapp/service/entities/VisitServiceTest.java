package com.zdzimi.registrationapp.service.entities;

import com.zdzimi.registrationapp.model.entities.DayTimetable;
import com.zdzimi.registrationapp.model.entities.MonthTimetable;
import com.zdzimi.registrationapp.model.entities.User;
import com.zdzimi.registrationapp.model.entities.Visit;
import com.zdzimi.registrationapp.repository.VisitRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class VisitServiceTest {

    private VisitRepo visitRepo;
    private VisitService visitService;

    @BeforeEach
    void setUp() {
        visitRepo = mock(VisitRepo.class);
        initMocks(this);
        visitService = new VisitService(visitRepo);
    }

    @Test
    void shouldFindByUserAndSort() {
        //  given
        User user = new User();
        //  01.01.2020 - 10:30  --- 3
        Visit visit1 = new Visit();
        visit1.setUser(user);
        visit1.setVisitTimeStart(LocalTime.of(10,30));
        DayTimetable dayTimetable1 = new DayTimetable();
        dayTimetable1.setDayOfMonth(1);
        MonthTimetable monthTimetable1 = new MonthTimetable();
        monthTimetable1.setMonth(1);
        monthTimetable1.setYear(2020);
        dayTimetable1.setMonthTimetable(monthTimetable1);
        visit1.setDayTimetable(dayTimetable1);
        //  02.01.2020 - 10:30  --- 1
        Visit visit2 = new Visit();
        visit2.setUser(user);
        visit2.setVisitTimeStart(LocalTime.of(10,30));
        DayTimetable dayTimetable2 = new DayTimetable();
        dayTimetable2.setDayOfMonth(2);
        MonthTimetable monthTimetable2 = new MonthTimetable();
        monthTimetable2.setMonth(1);
        monthTimetable2.setYear(2020);
        dayTimetable2.setMonthTimetable(monthTimetable2);
        visit2.setDayTimetable(dayTimetable2);
        //  01.01.2020 - 11:30  --- 2
        Visit visit3 = new Visit();
        visit3.setUser(user);
        visit3.setVisitTimeStart(LocalTime.of(11,30));
        DayTimetable dayTimetable3 = new DayTimetable();
        dayTimetable3.setDayOfMonth(1);
        MonthTimetable monthTimetable3 = new MonthTimetable();
        monthTimetable3.setMonth(1);
        monthTimetable3.setYear(2020);
        dayTimetable3.setMonthTimetable(monthTimetable3);
        visit3.setDayTimetable(dayTimetable3);
        when(visitRepo.findByUser(user)).thenReturn(Arrays.asList(visit1, visit2, visit3));
        //  when
        List<Visit> result = visitService.findByUser(user);
        //  then
        assertEquals(3, result.size());
        assertEquals(visit1, result.get(2));
        assertEquals(visit2, result.get(0));
        assertEquals(visit3, result.get(1));
        verify(visitRepo, times(1)).findByUser(user);
        verifyNoMoreInteractions(visitRepo);
    }

    @Test
    void findByDayTimetable() {
        //  given
        DayTimetable dayTimetable = new DayTimetable();
        Visit v1 = new Visit();
        Visit v2 = new Visit();
        Visit v3 = new Visit();
        Visit v4 = new Visit();
        v1.setDayTimetable(dayTimetable);
        v2.setDayTimetable(dayTimetable);
        v3.setDayTimetable(dayTimetable);
        v4.setDayTimetable(dayTimetable);
        when(visitRepo.findByDayTimetable(dayTimetable)).thenReturn(Arrays.asList(v1,v2,v3,v4));
        //  when
        List<Visit> result = visitService.findByDayTimetable(dayTimetable);
        //  then
        assertEquals(4, result.size());
        verify(visitRepo, times(1)).findByDayTimetable(dayTimetable);
        verifyNoInteractions(visitRepo);
    }

    @Test
    void findByDayTimetableWithoutUser() {
        //  given
        //  when
        //  then
    }

    @Test
    void findByUserAndId() {
        //  given
        //  when
        //  then
    }

    @Test
    void findByDayTimetableAndId() {
        //  given
        //  when
        //  then
    }

    @Test
    void findAllInPlaceByYearMonthAndDay() {
        //  given
        //  when
        //  then
    }

    @Test
    void save() {
    }

    @Test
    void findByUserAndInstitution() {
        //  given
        //  when
        //  then
    }
}