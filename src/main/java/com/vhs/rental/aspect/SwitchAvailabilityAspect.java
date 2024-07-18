package com.vhs.rental.aspect;
import com.vhs.rental.entity.Rental;
import com.vhs.rental.entity.VHS;
import com.vhs.rental.service.RentalService;
import com.vhs.rental.service.VHSService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Aspect
@Component
public class SwitchAvailabilityAspect {
    private RentalService rentalService;
    private VHSService vhsService;
    private static final Logger logger = LoggerFactory.getLogger(SwitchAvailabilityAspect.class);

    @Autowired
    public SwitchAvailabilityAspect(RentalService rentalService, VHSService vhsService){
        this.rentalService=rentalService;
        this.vhsService = vhsService;
    }
    @After("execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))")
    public void setTodaysRentalsToUnavailable(){
        List<Rental> allRentals = rentalService.findAll();
        for(Rental r: allRentals){
            //A reservation starts today, so check if it's returned and set available stats accordingly
            if(r.getRentalDate().compareTo(LocalDate.now())==0){
                VHS theVhs = rentalService.findRentalByIdWithVhs(r.getId()).getVhs();
                //someone is late returning, so cancel reservation
                if(rentalService.checkIfUnavailable(theVhs.getId())){
                    logger.info("Canceling reservation with id: " + r.getId() + " due to unavailability of the reserved vhs.");
                    rentalService.deleteRental(r);
                }
                else {
                    logger.info("Starting reservation: " + r);
                    theVhs.setAvailable(false);
                    logger.info("Setting " + theVhs.getTitle() + " to unavailable.");
                    vhsService.save(theVhs);
                }
            }
        }

    }
}
