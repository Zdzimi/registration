package com.zdzimi.registrationapp.exception;

public class RepresentativeNotFoundException extends RuntimeException {

    public RepresentativeNotFoundException(String representativeUsername) {
        super("Could not find representative: " + representativeUsername);
    }
}
