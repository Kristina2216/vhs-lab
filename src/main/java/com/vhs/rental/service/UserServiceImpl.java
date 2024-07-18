package com.vhs.rental.service;

import com.vhs.rental.dao.UserDao;
import com.vhs.rental.dao.VHSDao;
import com.vhs.rental.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService{
    private UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao){
        this.userDao=userDao;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Integer findNumOfRentalsByUserId(int theId) {
        return userDao.findNumOfRentalsByUserId(theId);
    }

    @Override
    public User findById(int theId) {
        return userDao.findById(theId);
    }
}
