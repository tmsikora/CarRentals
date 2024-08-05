package com.example.lab4_JEE_Spring.controllers;

import com.example.lab4_JEE_Spring.commands.CustomerCommand;
import com.example.lab4_JEE_Spring.converters.CustomerCommandToCustomer;
import com.example.lab4_JEE_Spring.converters.CustomerToCustomerCommand;
import com.example.lab4_JEE_Spring.model.Car;
import com.example.lab4_JEE_Spring.model.Customer;
import com.example.lab4_JEE_Spring.model.Rental;
import com.example.lab4_JEE_Spring.repositories.CarRepository;
import com.example.lab4_JEE_Spring.repositories.CustomerRepository;
import com.example.lab4_JEE_Spring.repositories.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
    private CarRepository carRepository;
    private CustomerRepository customerRepository;
    private RentalRepository rentalRepository;
    private CustomerCommandToCustomer customerCommandToCustomer;
    private CustomerToCustomerCommand customerToCustomerCommand;

    public CustomerController(CarRepository carRepository, CustomerRepository customerRepository, RentalRepository rentalRepository, CustomerCommandToCustomer customerCommandToCustomer, CustomerToCustomerCommand customerToCustomerCommand) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
        this.customerCommandToCustomer = customerCommandToCustomer;
        this.customerToCustomerCommand = customerToCustomerCommand;
    }

    @RequestMapping(value = {"/customers", "/customer/list"})
    public String getCustomers(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customer/list";
    }

    @RequestMapping("/customer/{id}/rentals")
    public String getCustomerRentals(Model model, @PathVariable("id") Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            model.addAttribute("rentals", rentalRepository.findByCustomerId(customer.get().getId()));
            model.addAttribute("filter", "customer: " + customer.get().getFirstName() + " " + customer.get().getLastName());
        } else {
            model.addAttribute("rentals", new ArrayList<>());
            model.addAttribute("filter", "customer for this id doesn't exist");
        }

        return "rental/list";
    }

    @RequestMapping("/customer/{id}/show")
    public String getCustomerDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("customer", customerRepository.findById(id).get());
        return "customer/show";
    }

    @GetMapping("/customer/new")
    public String newCustomer(Model model){
        model.addAttribute("customer", new CustomerCommand());
        return "customer/addedit";
    }

    @Transactional
    @RequestMapping("/customer/{id}/delete")
    public String deleteCustomer(@PathVariable("id") Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            List<Rental> rentals = rentalRepository.findByCustomer(customer);
            for (Rental rental : rentals) {
                Car car = rental.getCar();
                if (car != null) {
                    car.setStatus("Available");
                    carRepository.save(car);
                }
            }
            rentalRepository.deleteAllByCustomer(customer);
            customerRepository.delete(customer);
        }
        return "redirect:/customers";
    }

    @GetMapping("/customer/{id}/edit")
    public String editCustomer(Model model, @PathVariable("id") Long id){
        Customer customer = customerRepository.findById(id).get();
        model.addAttribute("customer", customerToCustomerCommand.convert(customer));
        return "customer/addedit";
    }

    @PostMapping("/customer/")
    public String saveOrUpdate(@ModelAttribute CustomerCommand command){
        Customer detachedCustomer = customerCommandToCustomer.convert(command);
        Customer savedCustomer = customerRepository.save(detachedCustomer);

        return "redirect:/customer/" + savedCustomer.getId() + "/show";
    }
}
