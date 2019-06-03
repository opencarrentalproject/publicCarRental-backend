package com.publicCarRentalDemo.publicCarRentalBackend.car;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, String> {
}
