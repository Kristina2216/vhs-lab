package com.vhs.rental.service;

import com.vhs.rental.entity.User;
import com.vhs.rental.entity.VHS;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findByUsername(String username);
    Integer findNumOfRentalsByUserId(int theId);

    User findById(int theId);
}
