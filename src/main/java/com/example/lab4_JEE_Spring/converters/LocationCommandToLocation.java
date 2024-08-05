package com.example.lab4_JEE_Spring.converters;

import com.example.lab4_JEE_Spring.commands.LocationCommand;
import com.example.lab4_JEE_Spring.model.Location;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationCommandToLocation implements Converter<LocationCommand, Location> {

    @Synchronized
    @Nullable
    @Override
    public Location convert(LocationCommand source) {
        if (source == null) {
            return null;
        }

        final Location location = new Location();
        location.setId(source.getId());
        location.setLocationName(source.getLocationName());
        location.setCity(source.getCity());
        location.setAddress(source.getAddress());

        return location;
    }
}
