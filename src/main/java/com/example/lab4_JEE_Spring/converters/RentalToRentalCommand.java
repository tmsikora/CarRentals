package com.example.lab4_JEE_Spring.converters;

import com.example.lab4_JEE_Spring.commands.RentalCommand;
import com.example.lab4_JEE_Spring.model.Rental;
import com.example.lab4_JEE_Spring.repositories.CarRepository;
import com.example.lab4_JEE_Spring.repositories.CustomerRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RentalToRentalCommand implements Converter<Rental, RentalCommand> {

    private CustomerRepository customerRepository;
    private CarRepository carRepository;

    public RentalToRentalCommand(CustomerRepository customerRepository, CarRepository carRepository) {
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public RentalCommand convert(Rental source) {
        if (source == null) {
            return null;
        }

        final RentalCommand rentalCommand = new RentalCommand();
        rentalCommand.setId(source.getId());
        rentalCommand.setRentalDate(source.getRentalDate());
        rentalCommand.setReturnDate(source.getReturnDate());
        rentalCommand.setPrice(source.getPrice());

        if (source.getCustomer() != null) {
            rentalCommand.setCustomerId(source.getCustomer().getId());
        }

        if (source.getCar() != null) {
            rentalCommand.setCarId(source.getCar().getId());
        }

        return rentalCommand;
    }
}
