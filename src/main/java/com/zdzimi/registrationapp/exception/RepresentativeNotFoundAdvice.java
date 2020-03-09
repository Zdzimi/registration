package com.zdzimi.registrationapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RepresentativeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(RepresentativeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String representativeNotfoundHandler(RepresentativeNotFoundException e){
        return e.getMessage();
    }
}
