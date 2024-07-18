package com.vhs.rental.controller;

import com.vhs.rental.exception.DeletedRentalException;
import com.vhs.rental.exception.RentalDateException;
import com.vhs.rental.exception.VHSErrorResponse;
import com.vhs.rental.exception.VHSNotAvailableException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public String handleException(VHSNotAvailableException exc, Model theModel){
        theModel.addAttribute("errorMessage", exc.getMessage());
        return "error";
    }

    @ExceptionHandler
    public String handleException(RentalDateException exc, Model theModel){
        theModel.addAttribute("errorMessage", exc.getMessage());
        return "error";
    }
    @ExceptionHandler
    public String handleException(SQLIntegrityConstraintViolationException exc, Model theModel){
        theModel.addAttribute("errorMessage", "This movie already exists in the database.");
        return "error";
    }

    @ExceptionHandler
    public String handleException(DeletedRentalException exc, Model theModel){
        theModel.addAttribute("errorMessage", exc.getMessage());
        return "error";
    }
    @ExceptionHandler
    public ResponseEntity<VHSErrorResponse> handleException(EntityNotFoundException exc){
        VHSErrorResponse error = new VHSErrorResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<VHSErrorResponse> handleException(Exception exc){
        VHSErrorResponse error = new VHSErrorResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
