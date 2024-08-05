package com.example.lab4_JEE_Spring.repositories;

import com.example.lab4_JEE_Spring.model.Car;
import com.example.lab4_JEE_Spring.model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findByCarMaker(String carMaker);
    List<Car> findByStatus(String status);
    List<Car> findByLocation(Location location);
}
