package com.zdzimi.registrationapp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Representative extends User{

    @OneToMany(mappedBy = "representative")
    private Set<MonthTimetable> monthTimetables;

    public Representative() {
    }

    public Representative(String username, String email, String password, Role role) {
        super(username, email, password, role);
    }

    public Representative(String username, String email, String password, Role role, Institution institution) {
        super(username, email, password, role, institution);
    }

    public Set<MonthTimetable> getMonthTimetables() {
        return monthTimetables;
    }

    public void setMonthTimetables(Set<MonthTimetable> monthTimetables) {
        this.monthTimetables = monthTimetables;
    }
}
