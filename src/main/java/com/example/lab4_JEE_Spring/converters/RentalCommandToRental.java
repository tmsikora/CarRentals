package com.example.lab4_JEE_Spring.converters;

import com.example.lab4_JEE_Spring.commands.RentalCommand;
import com.example.lab4_JEE_Spring.model.Car;
import com.example.lab4_JEE_Spring.model.Customer;
import com.example.lab4_JEE_Spring.model.Location;
import com.example.lab4_JEE_Spring.model.Rental;
import com.example.lab4_JEE_Spring.repositories.CarRepository;
import com.example.lab4_JEE_Spring.repositories.CustomerRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RentalCommandToRental implements Converter<RentalCommand, Rental> {

    private CustomerRepository customerRepository;
    private CarRepository carRepository;

    public RentalCommandToRental(CustomerRepository customerRepository, CarRepository carRepository) {
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Rental convert(RentalCommand source) {
        if (source == null) {
            return null;
        }

        final Rental rental = new Rental();
        rental.setId(source.getId());
        rental.setRentalDate(source.getRentalDate());
        rental.setReturnDate(source.getReturnDate());
        rental.setPrice(source.getPrice());

        Optional<Customer> customerOptional = customerRepository.findById(source.getCustomerId());
        customerOptional.ifPresent(rental::setCustomer);

        Optional<Car> carOptional = carRepository.findById(source.getCarId());
        carOptional.ifPresent(car -> {
            rental.setCar(car);

            Location carLocation = car.getLocation();
            if (carLocation != null) {
                rental.setPickupLocation(carLocation);
                rental.setDropoffLocation(carLocation);
            }
        });

        return rental;
    }
}
