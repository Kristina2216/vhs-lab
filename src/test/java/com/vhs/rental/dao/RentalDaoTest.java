package com.vhs.rental.dao;

import com.vhs.rental.entity.Rental;
import com.vhs.rental.entity.VHS;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/testing.properties")
public class RentalDaoTest {

    @Autowired
    private RentalDao rentalDao;
    @Autowired
    private UserDao userDao;


    @Test
    void givenRented_checkIfUnavailable() {
        Rental rental1 = new Rental();
        VHS movie1 = new VHS("El Camino", "Action, Crime, Drama", true);
        rental1.setVhs(movie1);
        rental1.setUser(userDao.findById(1));
        rental1.setRentalDate(LocalDate.of(2024, 6, 8));
        rental1.setDueDate(rental1.getRentalDate().plus(1, ChronoUnit.DAYS));
        rentalDao.save(rental1);

        Assertions.assertThat(rentalDao.checkIfUnavailable(rental1.getVhs().getId()) ).isEqualTo(true);
    }

}
