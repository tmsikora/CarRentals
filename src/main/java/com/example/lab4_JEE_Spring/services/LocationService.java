package com.example.lab4_JEE_Spring.services;

import com.example.lab4_JEE_Spring.commands.LocationCommand;

import java.util.List;
import java.util.Map;

public interface LocationService {

    public List<LocationCommand> getAllLocations();

    LocationCommand getLocationById(Long id);

    public List<LocationCommand> getLocationsByLocationName(String locationName);

    public List<LocationCommand> getLocationsByCity(String city);

    public LocationCommand createNewLocation(LocationCommand locationCommand);

    public LocationCommand updateLocation(Long Id, LocationCommand locationCommand);

    public LocationCommand partialUpdateLocation(Long id, Map<String, Object> updates);

    public void deleteLocationById(Long id);
}
