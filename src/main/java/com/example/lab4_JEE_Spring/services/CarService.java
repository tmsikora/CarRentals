package com.example.lab4_JEE_Spring.services;

import com.example.lab4_JEE_Spring.commands.CarCommand;

import java.util.List;
import java.util.Map;

public interface CarService {

    public List<CarCommand> getAllCars();

    CarCommand getCarById(Long id);

    public List<CarCommand> getCarsByCarMaker(String carMaker);

    public List<CarCommand> getCarsByStatus(String status);

    public CarCommand createNewCar(CarCommand carCommand);

    public CarCommand updateCar(Long id, CarCommand carCommand);

    public CarCommand partialUpdateCar(Long id, Map<String, Object> updates);

    public void deleteCarById(Long id);
}
