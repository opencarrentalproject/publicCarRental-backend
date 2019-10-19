package com.publicCarRentalDemo.publicCarRentalBackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.publicCarRentalDemo.publicCarRentalBackend.car.Car;
import com.publicCarRentalDemo.publicCarRentalBackend.car.CarRepository;

@RestController
public class CarController {

    private final CarRepository repository;

    public CarController(final CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cars")
    List<Car> all() {
        return repository.findAll();
    }

    @PostMapping("/cars")
    Car newCar(@RequestBody final Car car) {
        return repository.save(car);
    }

    // Single item

    @GetMapping("/cars/{id}")
    Car single(@PathVariable final String id) {

        return repository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable String id) {
        repository.deleteById(id);
    }
}
