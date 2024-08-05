package com.example.lab4_JEE_Spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Car {
    @Id
    @GeneratedValue
    private Long id;

    private String carMaker;
    private String carModel;
    private String productionYear;
    private String licensePlate;
    private String status;

    @ManyToOne
    private Location location;

    public Car() {
    }

    public Car(String carMaker, String carModel, String productionYear, String licensePlate, String status) {
        this.carMaker = carMaker;
        this.carModel = carModel;
        this.productionYear = productionYear;
        this.licensePlate = licensePlate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarMaker() {
        return carMaker;
    }

    public void setCarMaker(String carMaker) {
        this.carMaker = carMaker;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public void rent() {
        this.status = "Rented";
    }

    public void makeAvailable() {
        this.status = "Available";
    }
}