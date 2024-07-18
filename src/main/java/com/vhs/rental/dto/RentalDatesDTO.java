package com.vhs.rental.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public interface RentalDatesDTO {
    LocalDate getRentalDate();
    LocalDate getDueDate();

}
