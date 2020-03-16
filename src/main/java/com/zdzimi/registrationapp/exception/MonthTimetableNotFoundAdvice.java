package com.zdzimi.registrationapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MonthTimetableNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(MonthTimetableNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String monthTimetableNotFoundHandler(MonthTimetableNotFoundException e) {
        return e.getMessage();
    }
}
