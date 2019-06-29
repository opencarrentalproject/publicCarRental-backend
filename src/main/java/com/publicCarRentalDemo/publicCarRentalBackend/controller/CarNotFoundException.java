package com.publicCarRentalDemo.publicCarRentalBackend.controller;

class CarNotFoundException extends RuntimeException {

    CarNotFoundException(final String id) {
        super("Could not find car " + id);
    }
}
