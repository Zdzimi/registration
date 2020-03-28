package com.zdzimi.registrationapp.validator;

import com.zdzimi.registrationapp.model.entities.Visit;

import java.time.LocalTime;
import java.util.Set;

public class VisitValidator implements RegistrationAppValidator {

    private Set<Visit> visits;
    private LocalTime timeStart;
    private long visitTimeLength;

    public VisitValidator(Set<Visit> visits, LocalTime timeStart, long visitTimeLength) {
        this.visits = visits;
        this.timeStart = timeStart;
        this.visitTimeLength = visitTimeLength;
    }

    @Override
    public boolean isValid() {

        for (Visit visit : visits) {
            LocalTime visitTimeStart = visit.getVisitTimeStart();
            LocalTime visitTimeEnd = visit.getVisitTimeStart().plusMinutes(visit.getVisitTimeLength());

            if (!(heEndsBeforeMe(visitTimeEnd) || heStartsAfterMe(visitTimeStart))) {
                return false;
            }
        }
        return true;
    }

    private boolean heEndsBeforeMe(LocalTime visitTimeEnd) {
        return visitTimeEnd.isBefore(timeStart) || visitTimeEnd.equals(timeStart);
    }
    private boolean heStartsAfterMe(LocalTime visitTimeStart) {
        return visitTimeStart.isAfter(timeStart.plusMinutes(visitTimeLength))
                || visitTimeStart.equals(timeStart.plusMinutes(visitTimeLength));
    }

}
