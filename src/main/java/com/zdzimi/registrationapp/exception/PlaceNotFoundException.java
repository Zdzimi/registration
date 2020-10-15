package com.zdzimi.registrationapp.exception;

public class PlaceNotFoundException extends RuntimeException {

    public PlaceNotFoundException(String placeName) {
        super("Could not find place: " + placeName);
    }

    public PlaceNotFoundException(String institutionName, long placeId) {
        super("Could not find place: " + placeId + " from " + institutionName);
    }
}
