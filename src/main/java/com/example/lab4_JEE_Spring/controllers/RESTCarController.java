package com.example.lab4_JEE_Spring.controllers;

import com.example.lab4_JEE_Spring.commands.CarCommand;
import com.example.lab4_JEE_Spring.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/car")
public class RESTCarController {
    @Autowired
    private CarService carService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<CarCommand> getCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarCommand getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @GetMapping("findByCarMaker")
    @ResponseStatus(HttpStatus.OK)
    public List<CarCommand> getCarsByCarMaker(@RequestParam String carMaker) {
        return carService.getCarsByCarMaker(carMaker);
    }

    @GetMapping("findByStatus")
    @ResponseStatus(HttpStatus.OK)
    public List<CarCommand> getCarsByStatus(@RequestParam String status) {
        return carService.getCarsByStatus(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarCommand createNewCar(@RequestBody CarCommand carCommand) {
        return carService.createNewCar(carCommand);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarCommand updateCar(@PathVariable Long id, @RequestBody CarCommand carCommand) {
        return carService.updateCar(id, carCommand);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CarCommand> patchCar(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        CarCommand updatedCar = carService.partialUpdateCar(id, updates);
        if (updatedCar != null) {
            return ResponseEntity.ok(updatedCar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id);
    }
}
