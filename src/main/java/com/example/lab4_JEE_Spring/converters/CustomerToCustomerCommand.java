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
public class CustomerToCustomerCommand implements Converter<Customer, CustomerCommand> {

    private RentalRepository rentalRepository;

    public CustomerToCustomerCommand(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public CustomerCommand convert(Customer source) {
        if (source == null) {
            return null;
        }

        final CustomerCommand customerCommand = new CustomerCommand();
        customerCommand.setId(source.getId());
        customerCommand.setFirstName(source.getFirstName());
        customerCommand.setLastName(source.getLastName());
        customerCommand.setEmail(source.getEmail());
        customerCommand.setPhoneNumber(source.getPhoneNumber());
        customerCommand.setDriverLicenseNumber(source.getDriverLicenseNumber());

        if (source.getRentals() != null && !source.getRentals().isEmpty()) {
            for (Rental rental : source.getRentals()) {
                customerCommand.getRentalId().add(rental.getId());
            }
        }

        return customerCommand;
    }
}
