package com.example.lab4_JEE_Spring.converters;

import com.example.lab4_JEE_Spring.commands.CarCommand;
import com.example.lab4_JEE_Spring.model.Car;
import com.example.lab4_JEE_Spring.repositories.LocationRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CarToCarCommand implements Converter<Car, CarCommand> {

    private LocationRepository locationRepository;

    public CarToCarCommand(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public CarCommand convert(Car source) {
        if (source == null) {
            return null;
        }

        final CarCommand carCommand = new CarCommand();
        carCommand.setId(source.getId());
        carCommand.setCarMaker(source.getCarMaker());
        carCommand.setCarModel(source.getCarModel());
        carCommand.setProductionYear(source.getProductionYear());
        carCommand.setLicensePlate(source.getLicensePlate());
        carCommand.setStatus(source.getStatus());

        if (source.getLocation() != null) {
            carCommand.setLocationId(source.getLocation().getId());
        }

        return carCommand;
    }
}
