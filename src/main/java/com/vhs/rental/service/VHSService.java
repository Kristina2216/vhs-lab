package com.vhs.rental.service;

import com.vhs.rental.entity.VHS;

import java.util.List;

public interface VHSService {

    List<VHS> findAll();
    List<VHS> findByAvailability(boolean available);
    VHS findById(int theId);
    void save(VHS theVhs);
    void delete(VHS theVhs);
    boolean existsByIdAndTitleAndGenre(int id, String title, String genre);

}
