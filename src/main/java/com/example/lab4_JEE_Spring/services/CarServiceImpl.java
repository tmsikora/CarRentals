package com.example.lab4_JEE_Spring.services;

import com.example.lab4_JEE_Spring.commands.CarCommand;
import com.example.lab4_JEE_Spring.converters.CarCommandToCar;
import com.example.lab4_JEE_Spring.converters.CarToCarCommand;
import com.example.lab4_JEE_Spring.model.Car;
import com.example.lab4_JEE_Spring.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CarServiceImpl implements CarService {

    CarRepository carRepository;
    CarToCarCommand carToCarCommand;
    CarCommandToCar carCommandToCar;

    public CarServiceImpl(CarRepository carRepository, CarToCarCommand carToCarCommand, CarCommandToCar carCommandToCar) {
        this.carRepository = carRepository;
        this.carToCarCommand = carToCarCommand;
        this.carCommandToCar = carCommandToCar;
    }

    @Override
    public List<CarCommand> getAllCars() {
        return StreamSupport.stream(carRepository.findAll().spliterator(), false)
                .map(carToCarCommand::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CarCommand getCarById(Long id) {
        return carToCarCommand.convert(carRepository.findById(id).get());
    }

    @Override
    public List<CarCommand> getCarsByCarMaker(String carMaker) {
        return carRepository.findByCarMaker(carMaker)
                .stream()
                .map(carToCarCommand::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarCommand> getCarsByStatus(String status) {
        return carRepository.findByStatus(status)
                .stream()
                .map(carToCarCommand::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CarCommand createNewCar(CarCommand carCommand) {
        Car car = carCommandToCar.convert(carCommand);
        Car savedCar = carRepository.save(car);
        return carToCarCommand.convert(savedCar);
    }

    @Override
    public CarCommand updateCar(Long id, CarCommand carCommand) {
        Car car = carCommandToCar.convert(carCommand);
        car.setId(id);
        Car savedCar = carRepository.save(car);
        return carToCarCommand.convert(savedCar);
    }

    @Override
    public CarCommand partialUpdateCar(Long id, Map<String, Object> updates) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            updates.forEach((key, value) -> {
                switch (key) {
                    case "carMaker":
                        car.setCarMaker((String) value);
                        break;
                    case "carModel":
                        car.setCarModel((String) value);
                        break;
                    case "productionYear":
                        car.setProductionYear((String) value);
                        break;
                    case "licensePlate":
                        car.setLicensePlate((String) value);
                        break;
                    case "status":
                        car.setStatus((String) value);
                        break;
                    default:
                        break;
                }
            });
            Car savedCar = carRepository.save(car);
            return carToCarCommand.convert(savedCar);
        }
        return null;
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }
}
