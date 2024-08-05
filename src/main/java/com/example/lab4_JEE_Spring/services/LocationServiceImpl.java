package com.example.lab4_JEE_Spring.services;

import com.example.lab4_JEE_Spring.commands.LocationCommand;
import com.example.lab4_JEE_Spring.converters.LocationCommandToLocation;
import com.example.lab4_JEE_Spring.converters.LocationToLocationCommand;
import com.example.lab4_JEE_Spring.model.Location;
import com.example.lab4_JEE_Spring.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LocationServiceImpl implements LocationService {

    LocationRepository locationRepository;
    LocationToLocationCommand locationToLocationCommand;
    LocationCommandToLocation locationCommandToLocation;

    public LocationServiceImpl(LocationRepository locationRepository, LocationToLocationCommand locationToLocationCommand, LocationCommandToLocation locationCommandToLocation) {
        this.locationRepository = locationRepository;
        this.locationToLocationCommand = locationToLocationCommand;
        this.locationCommandToLocation = locationCommandToLocation;
    }

    @Override
    public List<LocationCommand> getAllLocations() {
        return StreamSupport.stream(locationRepository.findAll().spliterator(), false)
                .map(locationToLocationCommand::convert)
                .collect(Collectors.toList());
    }

    @Override
    public LocationCommand getLocationById(Long id) {
        return locationToLocationCommand.convert(locationRepository.findById(id).get());
    }

    @Override
    public List<LocationCommand> getLocationsByLocationName(String locationName) {
        return locationRepository.findByLocationName(locationName)
                .stream()
                .map(locationToLocationCommand::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationCommand> getLocationsByCity(String city) {
        return locationRepository.findByCity(city)
                .stream()
                .map(locationToLocationCommand::convert)
                .collect(Collectors.toList());
    }

    @Override
    public LocationCommand createNewLocation(LocationCommand locationCommand) {
        Location location = locationCommandToLocation.convert(locationCommand);
        Location savedLocation = locationRepository.save(location);
        return locationToLocationCommand.convert(savedLocation);
    }

    @Override
    public LocationCommand updateLocation(Long id, LocationCommand locationCommand) {
        Location location = locationCommandToLocation.convert(locationCommand);
        location.setId(id);
        Location savedLocation = locationRepository.save(location);
        return locationToLocationCommand.convert(savedLocation);
    }

    @Override
    public LocationCommand partialUpdateLocation(Long id, Map<String, Object> updates) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();
            updates.forEach((key, value) -> {
                switch (key) {
                    case "locationName":
                        location.setLocationName((String) value);
                        break;
                    case "city":
                        location.setCity((String) value);
                        break;
                    case "address":
                        location.setAddress((String) value);
                        break;
                    default:
                        break;
                }
            });
            Location savedLocation = locationRepository.save(location);
            return locationToLocationCommand.convert(savedLocation);
        }
        return null;
    }

    @Override
    public void deleteLocationById(Long id) {
        locationRepository.deleteById(id);
    }
}
