package com.example.lab4_JEE_Spring.converters;

import com.example.lab4_JEE_Spring.commands.LocationCommand;
import com.example.lab4_JEE_Spring.model.Location;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LocationToLocationCommand implements Converter<Location, LocationCommand> {

    @Synchronized
    @Nullable
    @Override
    public LocationCommand convert(Location source) {
        if (source == null) {
            return null;
        }

        final LocationCommand locationCommand = new LocationCommand();
        locationCommand.setId(source.getId());
        locationCommand.setLocationName(source.getLocationName());
        locationCommand.setCity(source.getCity());
        locationCommand.setAddress(source.getAddress());

        return locationCommand;
    }
}
