
package com.vhs.rental.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
@Table(name="rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name="vhs_id")
    private VHS vhs;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name="user_id")
    private User user;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message="you cannot select a date that's in the past")
    @Column(name="rental_date")
    @NotNull(message = "field is required")
    private LocalDate rentalDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Future(message = "the due date must be in the future!")
    @Column(name="due_date")
    private LocalDate dueDate;

    public Rental(){}

    public Rental(VHS vhs, User user, LocalDate rentalDate, LocalDate dueDate) {
        this.vhs = vhs;
        this.user = user;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
    }

    public VHS getVhs() {
        return vhs;
    }

    public void setVhs(VHS vhs) {
        this.vhs = vhs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isActive(){
        return (rentalDate.isBefore(LocalDate.now()) || LocalDate.now().compareTo(rentalDate)==0);
    }


    @Override
    public String toString() {
        return "Rental{" +
                "vhs=" + vhs +
                ", user=" + user +
                ", startDate=" + rentalDate +
                ", endDate=" + dueDate +
                '}';
    }
}
