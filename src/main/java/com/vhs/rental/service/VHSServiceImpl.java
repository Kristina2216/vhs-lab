package com.vhs.rental.service;

import com.vhs.rental.dao.VHSDao;
import com.vhs.rental.entity.VHS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VHSServiceImpl implements VHSService{

    private VHSDao vhsDao;
    @Autowired
    public VHSServiceImpl(VHSDao vhsDao){
        this.vhsDao=vhsDao;
    }
    @Override
    public List<VHS> findAll() {
        return vhsDao.findAll();
    }

    @Override
    public List<VHS> findByAvailability(boolean available) {
       return vhsDao.findByAvailability(available);
    }

    @Override
    public VHS findById(int theId) {
        return vhsDao.findById(theId);
    }

    @Override
    public void save(VHS theVhs) {
        vhsDao.save(theVhs);
    }

    @Override
    public void delete(VHS theVhs) {
        vhsDao.delete(theVhs);
    }

    @Override
    public boolean existsByIdAndTitleAndGenre(int id, String title, String genre){
        return vhsDao.existsByIdAndTitleAndGenre(id, title, genre);
    }

}
