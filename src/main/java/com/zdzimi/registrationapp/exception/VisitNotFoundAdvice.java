package com.zdzimi.registrationapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class VisitNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(VisitNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String visitNotFoundHandler(VisitNotFoundException e) {
        return e.getMessage();
    }
}
