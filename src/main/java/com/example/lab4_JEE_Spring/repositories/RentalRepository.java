package com.example.lab4_JEE_Spring.repositories;

import com.example.lab4_JEE_Spring.model.Customer;
import com.example.lab4_JEE_Spring.model.Rental;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends CrudRepository<Rental, Long> {
    List<Rental> findByCustomerId(Long customerId);
    Optional<Rental> getFirstByCustomerId(Long customerId);
    void deleteAllByCustomer(Customer customer);
    List<Rental> findByCustomer(Customer customer);
}
