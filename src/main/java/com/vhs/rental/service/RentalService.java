package com.vhs.rental.service;

import com.vhs.rental.dto.RentalDatesDTO;
import com.vhs.rental.entity.Rental;

import java.util.List;

public interface RentalService {
    List<Rental> findAll();
    List<Rental> findRentalsByUserId(int theId);
    Rental findRentalByIdWithVhs(int theId);
    void deleteRental(Rental theRental);
    void save(Rental theRental);
    List<Rental> findRentalsByVhsId(int theId);
    List<RentalDatesDTO> findAllReservedDatesByVhsId(int vhsId, int rentalId);
    boolean checkIfUnavailable(int vhsId);
}
