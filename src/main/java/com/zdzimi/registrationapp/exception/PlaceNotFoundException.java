package com.zdzimi.registrationapp.exception;

public class PlaceNotFoundException extends RuntimeException {

    public PlaceNotFoundException(String placeName) {
        super("Could not find place: " + placeName);
    }
}
