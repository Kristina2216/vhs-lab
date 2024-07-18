package com.vhs.rental.dao;

import com.vhs.rental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    @Query("Select COUNT(*) from Rental r where r.user.id=:theId")
    Integer findNumOfRentalsByUserId(int theId);
    User findById(int theId);

}
