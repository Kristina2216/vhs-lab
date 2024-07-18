package com.vhs.rental.Utils;

import com.vhs.rental.entity.Rental;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Component
public class PriceCalculator {
    @Value("${app.rentalFee}")
    private int rentalFee;
    @Value("${app.delayFee}")
    private int delayFee;

    PriceCalculator(){
    }

    public List<Integer> calculateRentalPrices(List<Rental> rentals){
        List<Integer> prices = new ArrayList<>();
        for(Rental r: rentals){
            int delay= (int)(ChronoUnit.DAYS.between(r.getDueDate(), LocalDate.now()));
            int additionalFee = delay>1? delay*delayFee : 0;
            int price = rentalFee + additionalFee;
            prices.add(price);
        }
        return prices;
    }

}
