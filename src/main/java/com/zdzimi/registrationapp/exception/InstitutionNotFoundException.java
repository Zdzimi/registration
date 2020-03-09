package com.zdzimi.registrationapp.exception;

public class InstitutionNotFoundException extends RuntimeException {

    public InstitutionNotFoundException(String institutionName) {
        super("Could not find institution: " + institutionName);
    }
}
