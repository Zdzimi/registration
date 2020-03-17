package com.zdzimi.registrationapp.model;

import java.time.LocalTime;

public class Day {

    private int dayNumber;
    private String placeName;
    private LocalTime tinmeStart;
    private LocalTime tinmeEnd;

    public Day() {
    }

    public Day(int dayNumber) {
        this.dayNumber = dayNumber;
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

    public LocalTime getTinmeStart() {
        return tinmeStart;
    }

    public void setTinmeStart(LocalTime tinmeStart) {
        this.tinmeStart = tinmeStart;
    }

    public LocalTime getTinmeEnd() {
        return tinmeEnd;
    }

    public void setTinmeEnd(LocalTime tinmeEnd) {
        this.tinmeEnd = tinmeEnd;
    }
}
