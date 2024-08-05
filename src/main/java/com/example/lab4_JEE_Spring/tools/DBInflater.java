package com.example.lab4_JEE_Spring.tools;

import com.example.lab4_JEE_Spring.model.*;
import com.example.lab4_JEE_Spring.repositories.CustomerRepository;
import com.example.lab4_JEE_Spring.repositories.CarRepository;
import com.example.lab4_JEE_Spring.repositories.LocationRepository;
import com.example.lab4_JEE_Spring.repositories.RentalRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DBInflater implements ApplicationListener<ContextRefreshedEvent> {

    private CarRepository carRepository;
    private CustomerRepository customerRepository;
    private LocationRepository locationRepository;
    private RentalRepository rentalRepository;

    public DBInflater(CarRepository carRepository, CustomerRepository customerRepository, LocationRepository locationRepository, RentalRepository rentalRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.locationRepository = locationRepository;
        this.rentalRepository = rentalRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        // Locations
        Location location1 = new Location("KatowiceRent", "Katowice", "Testowa 20");
        Location location2 = new Location("GliwiceRent", "Gliwice", "Wynajmowa 3");
        Location location3 = new Location("ZabrzeRent", "Zabrze", "Samochodowa 7");

        locationRepository.saveAll(Arrays.asList(location1, location2, location3));

        // Cars
        Car car1 = new Car("Toyota", "Corolla", "2024", "SK123456", "Rented");
        car1.setLocation(location1);
        location1.getCars().add(car1);
        Car car2 = new Car("BMW", "M3", "2023", "SK123457", "Rented");
        car2.setLocation(location2);
        location2.getCars().add(car2);
        Car car3 = new Car("Subaru", "WRX STI", "2018", "SK123458", "Available");
        car3.setLocation(location1);
        location1.getCars().add(car3);
        Car car4 = new Car("Lamborghini", "Huracan", "2020", "SK123459", "Available");
        car4.setLocation(location1);
        location1.getCars().add(car4);

        carRepository.saveAll(Arrays.asList(car1, car2, car3, car4));

        // Customers
        Customer customer1 = new Customer("Jan", "Kowalski", "example1@mail.com", "987654321", "DV1234");
        Customer customer2 = new Customer("Piotr", "Nowak", "example2@mail.com", "987654322", "DV2234");
        Customer customer3 = new Customer("Michał", "Polak", "example3@mail.com", "987654323", "DV3234");

        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));

        // Rentals
        Rental rental1 = new Rental(LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 5), 1400);
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        car1.setStatus("Rented");
        rental1.setPickupLocation(car1.getLocation());
        rental1.setDropoffLocation(car1.getLocation());
        Rental rental2 = new Rental(LocalDate.of(2024, 5, 6), LocalDate.of(2024, 5, 11), 2200);
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        car2.setStatus("Rented");
        rental2.setPickupLocation(car2.getLocation());
        rental2.setDropoffLocation(car2.getLocation());

        rentalRepository.saveAll(Arrays.asList(rental1, rental2));
    }
}
