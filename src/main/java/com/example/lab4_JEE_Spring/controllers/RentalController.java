package com.example.lab4_JEE_Spring.controllers;

import com.example.lab4_JEE_Spring.commands.RentalCommand;
import com.example.lab4_JEE_Spring.converters.RentalCommandToRental;
import com.example.lab4_JEE_Spring.converters.RentalToRentalCommand;
import com.example.lab4_JEE_Spring.model.Car;
import com.example.lab4_JEE_Spring.model.Rental;
import com.example.lab4_JEE_Spring.repositories.CarRepository;
import com.example.lab4_JEE_Spring.repositories.CustomerRepository;
import com.example.lab4_JEE_Spring.repositories.RentalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class RentalController {
    private RentalRepository rentalRepository;
    private CarRepository carRepository;
    private CustomerRepository customerRepository;
    private RentalToRentalCommand rentalToRentalCommand;
    private RentalCommandToRental rentalCommandToRental;

    public RentalController(RentalRepository rentalRepository, CarRepository carRepository, CustomerRepository customerRepository, RentalToRentalCommand rentalToRentalCommand, RentalCommandToRental rentalCommandToRental) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.rentalToRentalCommand = rentalToRentalCommand;
        this.rentalCommandToRental = rentalCommandToRental;
    }

    @GetMapping
    @RequestMapping(value = {"/rentals", "rental/list"})
    public String getRentals(Model model) {
        model.addAttribute("rentals", rentalRepository.findAll());
        return "rental/list";
    }

    @GetMapping
    @RequestMapping("/rental/{id}/show")
    public String getRentalDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("rental", rentalRepository.findById(id).get());
        return "rental/show";
    }

    @GetMapping
    @RequestMapping("/rental/new")
    public String newRental(Model model){
        model.addAttribute("rental", new RentalCommand());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("cars", carRepository.findByStatus("Available"));
        return "rental/addedit";
    }

    @RequestMapping("/rental/{id}/delete")
    public String deleteRental(@PathVariable("id") Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found with ID: " + id));
        rentalRepository.deleteById(id);
        updateCarStatus(rental.getCar(), "Available");
        return "redirect:/rentals";
    }

    private void updateCarStatus(Car car, String status) {
        car.setStatus(status);
        carRepository.save(car);
    }

    @GetMapping("/rental/{id}/edit")
    public String editRental(Model model, @PathVariable("id") Long id){
        Rental rental = rentalRepository.findById(id).get();

        Car currentCar = rental.getCar();
        List<Car> availableCars = carRepository.findByStatus("Available");
        if (!availableCars.contains(currentCar)) {
            availableCars.add(currentCar); // Ensure the current car is included
        }

        model.addAttribute("rental", rentalToRentalCommand.convert(rental));
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("cars", availableCars);
        return "rental/addedit";
    }

    @PostMapping("/rental/")
    public String saveOrUpdate(@ModelAttribute RentalCommand command){
        Optional<Rental> rentalOptional = rentalRepository.getFirstByCustomerId(command.getCustomerId());

        if (!rentalOptional.isPresent() || command.getId() != null) {
            Rental detachedRental = rentalCommandToRental.convert(command);

            if (detachedRental.getId() != null) {
                Rental existingRental = rentalRepository.findById(detachedRental.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Rental not found with ID: " + detachedRental.getId()));
                updateCarStatus(existingRental.getCar(), "Available");
            }

            Rental savedRental = rentalRepository.save(detachedRental);
            updateCarStatus(savedRental.getCar(), "Rented");
            return "redirect:/rental/" + savedRental.getId() + "/show";
        } else {
            //TODO: error message to template
            System.out.println("Sorry, there's such rental in db");
            return "redirect:/rental/" + rentalOptional.get().getId() + "/show";
        }
    }
}
