package com.publicCarRentalDemo.publicCarRentalBackend.car;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Arrays;

import com.publicCarRentalDemo.publicCarRentalBackend.Color;
import com.publicCarRentalDemo.publicCarRentalBackend.infotainment.Infotainment;

@Getter
@Builder
public class Car {

    @Id
    private String id;

    private String brand;
    private String model;
    private String constructionYear;
    private Color color;
    private String fuelType;
    private int fuelConsumption;
    private int numberOfDoors;
    private int numberOfSeats;
    private int numberOfCylinders;
    private Infotainment[] infotainmentTypes;

    @PersistenceConstructor
    public Car(final String id, final String brand, final String model, final String constructionYear,
            final Color color, final String fuelType, final int fuelConsumption, final int numberOfDoors,
            final int numberOfSeats, final int numberOfCylinders, final Infotainment[] infotainmentTypes) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.constructionYear = constructionYear;
        this.color = color;
        this.fuelType = fuelType;
        this.fuelConsumption = fuelConsumption;
        this.numberOfDoors = numberOfDoors;
        this.numberOfSeats = numberOfSeats;
        this.numberOfCylinders = numberOfCylinders;
        this.infotainmentTypes = infotainmentTypes;
    }

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
