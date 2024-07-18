package com.vhs.rental.controller;

import com.vhs.rental.dto.RentalDatesDTO;
import com.vhs.rental.Utils.PriceCalculator;
import com.vhs.rental.entity.Rental;
import com.vhs.rental.entity.User;
import com.vhs.rental.entity.VHS;
import com.vhs.rental.exception.DeletedRentalException;
import com.vhs.rental.exception.RentalDateException;
import com.vhs.rental.exception.VHSNotAvailableException;
import com.vhs.rental.service.RentalService;
import com.vhs.rental.service.UserService;
import com.vhs.rental.service.VHSService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/api")
public class RentalController {
    private VHSService vhsService;
    private UserService userService;
    private RentalService rentalService;
    private PriceCalculator priceCalculator;
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);


    @Autowired
    public RentalController(VHSService vhsService, UserService userService, RentalService rentalService, PriceCalculator priceCalculator){
        this.vhsService=vhsService;
        this.userService=userService;
        this.rentalService=rentalService;
        this.priceCalculator = priceCalculator;
    }
    @GetMapping("/rentals")
    public String showRentedList(Model theModel, Principal principal){
        User currentUser = userService.findByUsername(principal.getName());
        logger.info("Fetching all rentals for user with id: " + currentUser.getId());

        List<Rental> myRentals = rentalService.findRentalsByUserId(currentUser.getId());
        List<Integer> myPrices = priceCalculator.calculateRentalPrices(myRentals);

        theModel.addAttribute("prices", myPrices);
        theModel.addAttribute("rentals", myRentals);

        return "rentals_list";
    }
    @DeleteMapping("/rentals")
    public String returnVhs(@RequestParam("rentalId") int theId){
        Rental returnedRental = rentalService.findRentalByIdWithVhs(theId);;
        if(returnedRental==null)
            throw new DeletedRentalException("Sorry, looks like the selected VHS no longer exists.");

        //set available if it's a return
        if(returnedRental.isActive()) {
            returnedRental.getVhs().setAvailable(true);
            logger.info("Returning vhs: " + returnedRental.getVhs());
        }
        logger.info("Deleting rental: " + returnedRental);
        rentalService.deleteRental(returnedRental);
        return "redirect:/api/rentals";
    }

    @GetMapping("/rentals/addRentalForm/{vhsId}")
    public String showRentalForm(@PathVariable("vhsId") int vhsId, Model theModel, Principal principal){
        User currentUser = userService.findByUsername(principal.getName());
        Rental newRental = new Rental();
        newRental.setUser(currentUser);
        VHS rentedVHS= vhsService.findById(vhsId);
        if(rentedVHS==null){
            throw new EntityNotFoundException("No vhs with the id "+ vhsId + " found.");
        }

        newRental.setVhs(rentedVHS);
        theModel.addAttribute("theRental", newRental);
        logger.info("User " + currentUser.getUsername() +" renting vhs: " + rentedVHS);
        return "add_rental";
    }

    @PostMapping("/rentals")
    public String rentVhs(@Valid @ModelAttribute("theRental") Rental rental, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "add_rental";
        }

        //add due date ad start date + 1 if creating a new reservation
        if(rental.getDueDate()==null)
            rental.setDueDate(rental.getRentalDate().plus(1, ChronoUnit.DAYS));
        else{
            if(rental.getDueDate().isBefore(rental.getRentalDate()))
                throw new RentalDateException("The due date cannot be earlier than the rental date!");
        }
        //check if the selected days are available
        VHS selectedVhs = rental.getVhs();
        List<RentalDatesDTO> unavailable = rentalService.findAllReservedDatesByVhsId(rental.getVhs().getId(), rental.getId());
        for(RentalDatesDTO date: unavailable) {
            if (isWithinRange(rental.getRentalDate(), date.getRentalDate(), date.getDueDate()) || isWithinRange(rental.getDueDate(), date.getRentalDate(), date.getDueDate())){
                throw new VHSNotAvailableException("Sorry, looks like " + selectedVhs.getTitle() + " isn't available for the selected dates.");
            }
        }

        //if the renting starts now, set vhs to unavailable
        if(LocalDate.now().compareTo(rental.getRentalDate())==0) {
            if(!selectedVhs.isAvailable()) throw new VHSNotAvailableException("Sorry, looks like the selected VHS hasn't been returned yet.");
            logger.info("Setting vhs " + selectedVhs.getTitle()+ " to unavailable.");
            selectedVhs.setAvailable(false);
            vhsService.save(selectedVhs);
        }
        //save to db
        logger.info("Creating rental: " + rental);
        rentalService.save(rental);

        return "redirect:/api/rentals";
    }
    @PutMapping("/rentals")
    public String updateRental(@RequestParam("rentalId") int rentalId, Model theModel){
        Rental selectedRental = rentalService.findRentalByIdWithVhs(rentalId);
        logger.info("Updating Rental: " + selectedRental);
        if(selectedRental==null)
            throw new DeletedRentalException("Sorry, looks like the selected reservation no longer exists.");

        selectedRental.setDueDate(null);
        theModel.addAttribute("theRental", selectedRental);
        return "add_rental";
    }
    boolean isWithinRange(LocalDate testDate, LocalDate startDate, LocalDate endDate) {
        return !(testDate.isBefore(startDate) || testDate.isAfter(endDate));
    }
}
