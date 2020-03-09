package com.zdzimi.registrationapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class InstitutionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(InstitutionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String institutionNotFoundHandler(InstitutionNotFoundException e){
        return e.getMessage();
    }
}
