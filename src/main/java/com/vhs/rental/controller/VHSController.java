package com.vhs.rental.controller;
import com.vhs.rental.entity.Rental;
import com.vhs.rental.entity.VHS;
import com.vhs.rental.service.RentalService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class VHSController {

    private VHSService vhsService;
    private RentalService rentalService;
    private static final Logger logger = LoggerFactory.getLogger(VHSController.class);


    @Autowired
    public VHSController(VHSService vhsService, RentalService rentalService){
        this.vhsService=vhsService;
        this.rentalService=rentalService;
    }
    @GetMapping("/")
    public String showHomePage(Model theModel){
        return "home";
    }

    @GetMapping("/vhs")
    public String showAvailableList(Model theModel, @RequestParam("available") boolean available){
        List<VHS> allVhs = new ArrayList<>();
        if(available){
            allVhs = vhsService.findByAvailability(true);
            logger.info("Fetching currently available Vhs");
        }
        else{
            allVhs = vhsService.findAll();
            logger.info("Fetching all Vhs");
        }
        theModel.addAttribute("vhs", allVhs);
        return "vhs_list";
    }

    @PostMapping("/vhs")
    public String saveVhs(@Valid @ModelAttribute("theVhs") VHS theVhs, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "add_vhs";
        vhsService.save(theVhs);
        logger.info("Saving: " + theVhs);
        return "redirect:/api/allVhs";
    }

    @DeleteMapping("/vhs")
    public String deleteVhs(@RequestParam("vhsId") int vhsId, Model theModel){
        VHS selectedVHS= vhsService.findById(vhsId);;
        if (selectedVHS==null)
            throw new EntityNotFoundException("No vhs with the id "+ vhsId + " found.");

        if(!selectedVHS.isAvailable()){
            List<Rental> associatedRentals = rentalService.findRentalsByVhsId(vhsId);
            for(Rental r: associatedRentals) {
                rentalService.deleteRental(r);
                logger.info("Deleting rental: " +r);
            }
        }
        vhsService.delete(selectedVHS);
        logger.info("Deleting VHS: " + selectedVHS);
        return "redirect:/api/allVhs";
    }

    @PutMapping("/vhs")
    public String updateVhs(@RequestParam("vhsId") int vhsId, Model theModel){
        VHS selectedVHS = vhsService.findById(vhsId);
        logger.info("Updating VHS: " + selectedVHS);
        if(selectedVHS==null)
            throw new EntityNotFoundException("No vhs with the id "+ vhsId + " found.");

        theModel.addAttribute("theVhs", selectedVHS);
        return "add_vhs";
    }
    @GetMapping("/addVhsForm")
    public String addVhsForm(Model theModel){
        VHS newVhs = new VHS();
        newVhs.setAvailable(true);
        logger.info("Creating new VHS.");
        theModel.addAttribute("theVhs", newVhs);
        return "add_vhs";
    }
}
