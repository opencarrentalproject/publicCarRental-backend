package com.publicCarRentalDemo.publicCarRentalBackend.car;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.Arrays;

@Getter
@Builder
public class Car {
    @Id
    private final String id;

    private final String brand;
    private final String model;
    private final String constructionYear;
    private final String color;
    private final String fuelType;
    private final String fuelConsumption;
    private final int numberOfDoors;
    private final int numberOfSeats;
    private final int numberOfCylinders;
    private final String[] infotainmentTypes;

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", constructionYear='" + constructionYear + '\'' +
                ", color='" + color + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", fuelConsumption='" + fuelConsumption + '\'' +
                ", numberOfDoors=" + numberOfDoors +
                ", numberOfSeats=" + numberOfSeats +
                ", numberOfCylinders=" + numberOfCylinders +
                ", infotainmentTypes=" + Arrays.toString(infotainmentTypes) +
                '}';
    }
}
