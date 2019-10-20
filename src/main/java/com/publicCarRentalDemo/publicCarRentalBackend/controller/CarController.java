package com.publicCarRentalDemo.publicCarRentalBackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
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
    List<Car> getAllCars() {
        return repository.findAll();
    }

    @PostMapping("/cars")
    Car newCar(@RequestBody final Car car) {
        return repository.save(car);
    }

    @GetMapping("/cars/{id}")
    Car getCar(@PathVariable final String id) {

        return repository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable String id) {

        Optional<Car> carOptional = repository.findById(id);

        if (!carOptional.isPresent())
            return ResponseEntity.notFound().build();


        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@RequestBody Car car, @PathVariable String id) {

        Optional<Car> carOptional = repository.findById(id);

        if (!carOptional.isPresent())
            return ResponseEntity.notFound().build();

        Car toUpdate = car.toBuilder().id(id).build();


        repository.save(toUpdate);

        return ResponseEntity.noContent().build();
    }
}
