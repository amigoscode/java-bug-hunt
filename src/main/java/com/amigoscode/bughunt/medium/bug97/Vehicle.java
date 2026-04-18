package com.amigoscode.bughunt.medium.bug97;

import java.util.Objects;

/**
 * Represents a vehicle with a speed that can be accelerated and braked.
 * Provides a description method that reports the vehicle's current state.
 *
 * Subclasses can extend this to add specialized vehicle behavior.
 */
public class Vehicle {

    private final String make;
    private final String model;
    protected int speed;

    /**
     * Creates a vehicle with the given make and model, starting at speed 0.
     *
     * @param make  the manufacturer name
     * @param model the model name
     */
    public Vehicle(String make, String model) {
        this.make = Objects.requireNonNull(make, "make must not be null");
        this.model = Objects.requireNonNull(model, "model must not be null");
        this.speed = 0;
    }

    /**
     * Accelerates the vehicle by the given amount.
     *
     * @param amount the speed increase in km/h
     * @throws IllegalArgumentException if amount is negative
     */
    public void accelerate(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must not be negative");
        }
        speed += amount;
    }

    /**
     * Applies brakes, reducing speed by the given amount.
     * Speed cannot go below zero.
     *
     * @param amount the speed decrease in km/h
     * @throws IllegalArgumentException if amount is negative
     */
    public void brake(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must not be negative");
        }
        speed = Math.max(0, speed - amount);
    }

    /**
     * Returns the current speed of the vehicle.
     *
     * @return speed in km/h
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns a human-readable description of the vehicle and its current speed.
     * This method reads the {@code speed} field directly.
     *
     * @return a description string
     */
    public String describe() {
        return make + " " + model + " going at " + speed + " km/h";
    }

    /**
     * Returns the make of the vehicle.
     */
    public String getMake() {
        return make;
    }

    /**
     * Returns the model of the vehicle.
     */
    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return describe();
    }
}
