package com.example.lab4_JEE_Spring.controllers;

import com.example.lab4_JEE_Spring.commands.LocationCommand;
import com.example.lab4_JEE_Spring.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/location")
public class RESTLocaltionController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationCommand> getLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationCommand getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/findByCity")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationCommand> getLocationsByCity(@RequestParam String city) {
        return locationService.getLocationsByCity(city);
    }

    @GetMapping("/findByLocationName")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationCommand> getLocationsByLocationName(@RequestParam String locationName) {
        return locationService.getLocationsByLocationName(locationName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationCommand createNewLocation(@RequestBody LocationCommand locationCommand) {
        return locationService.createNewLocation(locationCommand);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationCommand updateLocation(@PathVariable Long id, @RequestBody LocationCommand locationCommand) {
        return locationService.updateLocation(id, locationCommand);
    }

    @PatchMapping("{id}")
    public ResponseEntity<LocationCommand> patchLocation(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        LocationCommand updatedLocation = locationService.partialUpdateLocation(id, updates);
        if (updatedLocation != null) {
            return ResponseEntity.ok(updatedLocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocationById(@PathVariable Long id) {
        locationService.deleteLocationById(id);
    }
}
