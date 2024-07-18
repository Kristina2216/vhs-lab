package com.vhs.rental.dao;

import com.vhs.rental.entity.VHS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
@Repository
public interface VHSDao extends JpaRepository<VHS, Integer> {
    @Query("Select v from VHS v where v.available=:isAvailable")
    List<VHS> findByAvailability(boolean isAvailable);
    VHS findById(int theId);
    boolean existsByIdAndTitleAndGenre(int id, String title, String genre);

}

