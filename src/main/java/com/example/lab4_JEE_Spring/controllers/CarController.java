package com.example.lab4_JEE_Spring.controllers;

import com.example.lab4_JEE_Spring.commands.CarCommand;
import com.example.lab4_JEE_Spring.converters.CarCommandToCar;
import com.example.lab4_JEE_Spring.converters.CarToCarCommand;
import com.example.lab4_JEE_Spring.model.Car;
import com.example.lab4_JEE_Spring.repositories.CarRepository;
import com.example.lab4_JEE_Spring.repositories.LocationRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class CarController {
    private CarRepository carRepository;
    private LocationRepository locationRepository;
    private CarCommandToCar carCommandToCar;
    private CarToCarCommand carToCarCommand;

    public CarController(CarRepository carRepository, LocationRepository locationRepository, CarCommandToCar carCommandToCar, CarToCarCommand carToCarCommand) {
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
        this.carCommandToCar = carCommandToCar;
        this.carToCarCommand = carToCarCommand;
    }

    @GetMapping
    @RequestMapping(value = {"/cars", "car/list"})
    public String getCars(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "car/list";
    }

    @GetMapping
    @RequestMapping("/car/{id}/show")
    public String getCarDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("car", carRepository.findById(id).get());
        return "car/show";
    }

    @GetMapping
    @RequestMapping("/car/new")
    public String newCar(Model model){
        model.addAttribute("car", new CarCommand());
        model.addAttribute("locations", locationRepository.findAll());
        return "car/addedit";
    }

    @GetMapping
    @RequestMapping("/car/{id}/delete")
    public String deleteCar(@PathVariable("id") Long id, HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if (referer != null) {
            Pattern pattern = Pattern.compile("/location/(\\d+)/cars");
            Matcher matcher = pattern.matcher(referer);
            if (matcher.find()) {
                Long locationId = Long.parseLong(matcher.group(1));
                carRepository.deleteById(id);
                return "redirect:/location/" + locationId + "/cars";
            }
        }
        carRepository.deleteById(id);
        return "redirect:/cars";
    }

    @GetMapping("/car/{id}/edit")
    public String editCar(Model model, @PathVariable("id") Long id){
        Car car = carRepository.findById(id).get();
        model.addAttribute("car", carToCarCommand.convert(car));
        model.addAttribute("locations", locationRepository.findAll());
        return "car/addedit";
    }

    @PostMapping("/car")
    public String saveOrUpdate(@ModelAttribute CarCommand command){
        Car detachedCar = carCommandToCar.convert(command);
        Car savedCar = carRepository.save(detachedCar);

        return "redirect:/car/" + savedCar.getId() + "/show";
    }
}
