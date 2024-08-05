package com.example.lab4_JEE_Spring.repositories;

import com.example.lab4_JEE_Spring.model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Optional<Location> getFirstByLocationName(String name);
    Optional<Location> findByLocationName(String locationName);
    Optional<Location> findByCity(String city);
}
