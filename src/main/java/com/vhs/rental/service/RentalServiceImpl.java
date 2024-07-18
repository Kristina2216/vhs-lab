package com.vhs.rental.service;

import com.vhs.rental.dto.RentalDatesDTO;
import com.vhs.rental.dao.RentalDao;
import com.vhs.rental.entity.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService{
    private RentalDao rentalDao;

    @Autowired
    public RentalServiceImpl(RentalDao rentalDao){
        this.rentalDao=rentalDao;
    }

    @Override
    public List<Rental> findAll() {
        return rentalDao.findAll();
    }

    @Override
    public List<Rental> findRentalsByUserId(int theId) {
        return rentalDao.findRentalsByUserId(theId);
    }

    @Override
    public Rental findRentalByIdWithVhs(int theId) {
        return rentalDao.findRentalByIdWithVhs(theId);
    }

    @Override
    public void deleteRental(Rental theRental) {
        rentalDao.delete(theRental);
    }

    @Override
    public void save(Rental theRental) {
        rentalDao.save(theRental);
    }

    @Override
    public List<Rental> findRentalsByVhsId(int theId) {
        return rentalDao.findRentalsByVhsId(theId);
    }

    @Override
    public List<RentalDatesDTO> findAllReservedDatesByVhsId(int vhsId, int rentalId) {
        return rentalDao.findAllReservedDatesByVhsId(vhsId, rentalId);
    }

    @Override
    public boolean checkIfUnavailable(int vhsId) {
        return rentalDao.checkIfUnavailable(vhsId);
    }

}
