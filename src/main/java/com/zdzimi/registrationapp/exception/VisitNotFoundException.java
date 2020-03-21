package com.zdzimi.registrationapp.exception;

public class VisitNotFoundException extends RuntimeException {
    public VisitNotFoundException(long visitId) {
        super("Could not find visit no: " + visitId);
    }
}
