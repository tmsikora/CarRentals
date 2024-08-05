package com.example.lab4_JEE_Spring.repositories;

import com.example.lab4_JEE_Spring.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByEmail(String email);
    List<Customer> findByDriverLicenseNumber(String driverLicenseNumber);
}
