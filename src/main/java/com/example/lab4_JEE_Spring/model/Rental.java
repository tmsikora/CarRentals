package com.example.lab4_JEE_Spring.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Rental {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate rentalDate;
    private LocalDate returnDate;
    private int price;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Car car;

    @ManyToOne
    private Location pickupLocation;

    @ManyToOne
    private Location dropoffLocation;

    public Rental() {
    }

    public Rental(LocalDate rentalDate, LocalDate returnDate, int price) {
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Location getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(Location dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }
}
