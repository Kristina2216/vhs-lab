package com.vhs.rental.dao;

import com.vhs.rental.dto.RentalDatesDTO;
import com.vhs.rental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalDao extends JpaRepository<Rental, Integer> {
    @Query("Select r from Rental r JOIN FETCH r.vhs JOIN FETCH r.user where r.user.id=:theId")
    List<Rental> findRentalsByUserId(int theId);

    @Query("Select r from Rental r JOIN FETCH r.vhs where r.vhs.id=:theId")
    List<Rental> findRentalsByVhsId(int theId);

    @Query("Select r from Rental r JOIN FETCH r.vhs  where r.id=:theId")
    Rental findRentalByIdWithVhs(int theId);

    @Query("Select r.rentalDate AS rentalDate, r.dueDate as dueDate from Rental r where r.vhs.id=:vhsId and r.id!=:rentalId")
    List<RentalDatesDTO> findAllReservedDatesByVhsId(int vhsId, int rentalId);

    @Query("select new java.lang.Boolean(count(*) > 0) from Rental r WHERE (r.vhs.id=:vhsId AND r.dueDate < CURRENT_DATE)")
    boolean checkIfUnavailable(int vhsId);

}
