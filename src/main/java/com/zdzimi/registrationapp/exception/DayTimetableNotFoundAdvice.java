package com.zdzimi.registrationapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DayTimetableNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DayTimetableNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String dayTimetableNotFoundHandler(DayTimetableNotFoundException e) {
        return e.getMessage();
    }
}
