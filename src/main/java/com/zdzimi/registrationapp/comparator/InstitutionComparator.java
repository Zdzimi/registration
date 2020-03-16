package com.zdzimi.registrationapp.comparator;

import com.zdzimi.registrationapp.model.entities.Institution;

import java.util.Comparator;

public class InstitutionComparator implements Comparator<Institution> {

    @Override
    public int compare(Institution o1, Institution o2) {
        return o1.getInstitutionName().compareTo(o2.getInstitutionName());
    }
}
