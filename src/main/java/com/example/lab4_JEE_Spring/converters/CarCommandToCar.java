package com.example.lab4_JEE_Spring.converters;

import com.example.lab4_JEE_Spring.commands.CarCommand;
import com.example.lab4_JEE_Spring.model.Car;
import com.example.lab4_JEE_Spring.model.Location;
import com.example.lab4_JEE_Spring.repositories.LocationRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CarCommandToCar implements Converter<CarCommand, Car> {

    private LocationRepository locationRepository;

    public CarCommandToCar(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Car convert(CarCommand source) {
        if (source == null) {
            return null;
        }

        final Car car = new Car();
        car.setId(source.getId());
        car.setCarMaker(source.getCarMaker());
        car.setCarModel(source.getCarModel());
        car.setProductionYear(source.getProductionYear());
        car.setLicensePlate(source.getLicensePlate());
        car.setStatus(source.getStatus());

        if (source.getLocationId() != null) {
            Optional<Location> location = locationRepository.findById(source.getLocationId());
            location.ifPresent(car::setLocation);
        }

        return car;
    }
}
