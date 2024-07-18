package com.vhs.rental.controller;

import com.vhs.rental.Utils.PriceCalculator;
import com.vhs.rental.entity.Rental;
import com.vhs.rental.entity.User;
import com.vhs.rental.entity.VHS;
import com.vhs.rental.service.RentalService;
import com.vhs.rental.service.UserService;
import com.vhs.rental.service.VHSService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class AdminController {
    private UserService userService;
    private VHSService vhsService;
    private RentalService rentalService;
    private PriceCalculator priceCalculator;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(UserService userService, VHSService vhsService, RentalService rentalService, PriceCalculator priceCalculator){
        this.userService=userService;
        this.vhsService=vhsService;
        this.rentalService=rentalService;
        this.priceCalculator = priceCalculator;
    }

    @GetMapping("/allUsers")
    public String getUsers(Model theModel){
        List<User> allUsers = userService.findAll();

        List<Integer> numOfRentals = new ArrayList<>();
        for(User u: allUsers) numOfRentals.add(userService.findNumOfRentalsByUserId(u.getId()));

        theModel.addAttribute("users", allUsers);
        theModel.addAttribute("numOfRentals", numOfRentals);
        logger.info("Fetching all users.");
        return "users_list";
    }
    @GetMapping("/allVhs")
    public String getAllVhs(Model theModel){
        List<VHS> allAvailableVhs = vhsService.findAll();
        theModel.addAttribute("vhs", allAvailableVhs);
        logger.info("Fetching all vhs.");
        return "vhs_list";
    }
    @GetMapping("/rentals/{id}")
    public String showRentedList(@PathVariable("id") int theId, Model theModel){
        User selectedUser = userService.findById(theId);
        if(selectedUser==null) throw new EntityNotFoundException("No user with the id "+theId + " found.");

        List<Rental> myRentals = rentalService.findRentalsByUserId(theId);
        List<Integer> myPrices = priceCalculator.calculateRentalPrices(myRentals);

        theModel.addAttribute("rentals", myRentals);
        theModel.addAttribute("prices", myPrices);

        logger.info("Fetching all rentals for user with id: " + selectedUser.getId());
        return "rentals_list";
    }

    @GetMapping("/allRentals")
    public String getRentals(Model theModel){
        List<Rental> allRentals = rentalService.findAll();
        List<Integer> myPrices = priceCalculator.calculateRentalPrices(allRentals);

        theModel.addAttribute("rentals", allRentals);
        theModel.addAttribute("prices", myPrices);

        logger.info("Fetching all rentals.");
        return "rentals_list";
    }
}
