package com.example.lab4_JEE_Spring.controllers;

import com.example.lab4_JEE_Spring.commands.LocationCommand;
import com.example.lab4_JEE_Spring.converters.LocationCommandToLocation;
import com.example.lab4_JEE_Spring.converters.LocationToLocationCommand;
import com.example.lab4_JEE_Spring.model.Location;
import com.example.lab4_JEE_Spring.repositories.CarRepository;
import com.example.lab4_JEE_Spring.repositories.LocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class LocationController {
    private LocationRepository locationRepository;
    private CarRepository carRepository;
    private LocationCommandToLocation locationCommandToLocation;
    private LocationToLocationCommand locationToLocationCommand;

    public LocationController(LocationRepository locationRepository, CarRepository carRepository, LocationCommandToLocation locationCommandToLocation, LocationToLocationCommand locationToLocationCommand) {
        this.locationRepository = locationRepository;
        this.carRepository = carRepository;
        this.locationCommandToLocation = locationCommandToLocation;
        this.locationToLocationCommand = locationToLocationCommand;
    }

    @RequestMapping(value = {"/locations", "/location/list"})
    public String getLocations(Model model) {
        model.addAttribute("locations", locationRepository.findAll());
        return "location/list";
    }

    @RequestMapping("/location/{id}/cars")
    public String getLocationCars(Model model, @PathVariable("id") Long id) {
        Optional<Location> location = locationRepository.findById(id);

        if (location.isPresent()) {
            model.addAttribute("cars", carRepository.findByLocation(location.get()));
            model.addAttribute("filter", "location: " + location.get().getLocationName() + " " + location.get().getCity());
        } else {
            model.addAttribute("cars", new ArrayList<>());
            model.addAttribute("filter", "location for this id doesn't exist");
        }

        return "car/list";
    }

    @RequestMapping("/location/{id}/show")
    public String getLocationDetails(Model model, @PathVariable("id") Long id) {
        model.addAttribute("location", locationRepository.findById(id).get());
        return "location/show";
    }

    @GetMapping("/location/new")
    public String newLocation(Model model){
        model.addAttribute("location", new LocationCommand());
        return "location/addedit";
    }

    @RequestMapping("/location/{id}/delete")
    public String deleteLocation(@PathVariable("id") Long id) {
        locationRepository.deleteById(id);
        return "redirect:/locations";
    }

    @GetMapping("/location/{id}/edit")
    public String editLocation(Model model, @PathVariable("id") Long id){
        Location location = locationRepository.findById(id).get();
        model.addAttribute("location", locationToLocationCommand.convert(location));
        return "location/addedit";
    }

    @PostMapping("/location/")
    public String saveOrUpdate(@ModelAttribute LocationCommand command){
        Optional<Location> locationOptional = locationRepository.getFirstByLocationName(command.getLocationName());

        if (!locationOptional.isPresent() || command.getId() != null) {
            Location detachedLocation = locationCommandToLocation.convert(command);
            Location savedLocation = locationRepository.save(detachedLocation);
            return "redirect:/location/" + savedLocation.getId() + "/show";
        } else {
            //TODO: error message to template
            System.out.println("Sorry, there's such location in db");
            return "redirect:/location/" + locationOptional.get().getId() + "/show";
        }
    }
}
