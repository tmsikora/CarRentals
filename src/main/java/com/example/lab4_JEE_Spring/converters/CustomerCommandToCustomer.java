package com.example.lab4_JEE_Spring.converters;

import com.example.lab4_JEE_Spring.commands.CustomerCommand;
import com.example.lab4_JEE_Spring.model.Customer;
import com.example.lab4_JEE_Spring.model.Rental;
import com.example.lab4_JEE_Spring.repositories.RentalRepository;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandToCustomer implements Converter<CustomerCommand, Customer> {

    private RentalRepository rentalRepository;

    public CustomerCommandToCustomer(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Customer convert(CustomerCommand source) {
        if (source == null) {
            return null;
        }

        final Customer customer = new Customer();
        customer.setId(source.getId());
        customer.setFirstName(source.getFirstName());
        customer.setLastName(source.getLastName());
        customer.setEmail(source.getEmail());
        customer.setPhoneNumber(source.getPhoneNumber());
        customer.setDriverLicenseNumber(source.getDriverLicenseNumber());

        if (source.getRentalId() != null && !source.getRentalId().isEmpty()) {
            for (Long rentalId : source.getRentalId()) {
                Rental rental = rentalRepository.findById(rentalId).orElse(null);
                if (rental != null) {
                    customer.getRentals().add(rental);
                }
            }
        }

        return customer;
    }
}
