package com.zdzimi.registrationapp.model.template;

import java.time.LocalTime;

public class Day {

    private int dayNumber;
    private String placeName;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public Day() {
    }

    public Day(int dayNumber) {
        this.dayNumber = dayNumber;
        this.timeStart = LocalTime.of(0,0,0);
        this.timeEnd = LocalTime.of(0,0,0);
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }
}
