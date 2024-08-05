package com.example.lab4_JEE_Spring.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Location {
    @Id
    @GeneratedValue
    private Long id;

    private String locationName;
    private String city;
    private String address;

    @OneToMany(mappedBy = "location")
    private Set<Car> cars = new HashSet<>();

    public Location() {
    }

    public Location(String locationName, String city, String address) {
        this.locationName = locationName;
        this.city = city;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
