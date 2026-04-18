package com.amigoscode.bughunt.medium.bug97;

/**
 * An electric vehicle that extends {@link Vehicle} with battery management.
 * Tracks battery level and enforces speed limits based on remaining charge.
 *
 * BUG: This class accidentally declares its own {@code speed} field,
 * which shadows the parent's {@code protected int speed}. The subclass
 * methods read and write the child's field, but the parent's
 * {@code describe()} and {@code getSpeed()} methods read the parent's
 * field, which stays at 0.
 */
public class ElectricVehicle extends Vehicle {

    private int batteryLevel;
    private final int maxSpeed;

    // BUG: This shadows Vehicle.speed -- parent methods still see parent's field
    private int speed;

    /**
     * Creates an electric vehicle with a full battery and a maximum speed.
     *
     * @param make     the manufacturer name
     * @param model    the model name
     * @param maxSpeed the top speed in km/h
     */
    public ElectricVehicle(String make, String model, int maxSpeed) {
        super(make, model);
        this.maxSpeed = maxSpeed;
        this.batteryLevel = 100;
        this.speed = 0;
    }

    /**
     * Accelerates the electric vehicle, capped by max speed.
     * Each acceleration consumes battery proportional to the speed gained.
     *
     * BUG: Writes to the child's {@code speed} field, not the parent's.
     * So {@code describe()} (inherited from Vehicle) still reports 0.
     *
     * @param amount the speed increase in km/h
     */
    @Override
    public void accelerate(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must not be negative");
        }
        if (batteryLevel <= 0) {
            throw new IllegalStateException("battery is dead, cannot accelerate");
        }
        // BUG: writes to child's speed, not parent's
        speed = Math.min(speed + amount, maxSpeed);
        batteryLevel = Math.max(0, batteryLevel - (amount / 5));
    }

    /**
     * Applies regenerative braking. Recovers a small amount of battery.
     *
     * BUG: Reads/writes child's {@code speed} field, not parent's.
     *
     * @param amount the speed decrease in km/h
     */
    @Override
    public void brake(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must not be negative");
        }
        int actualReduction = Math.min(amount, speed);
        // BUG: reads/writes child's speed
        speed = Math.max(0, speed - amount);
        batteryLevel = Math.min(100, batteryLevel + (actualReduction / 10));
    }

    /**
     * Returns the current battery level as a percentage.
     *
     * @return battery level between 0 and 100
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * Returns the maximum speed of this electric vehicle.
     *
     * @return max speed in km/h
     */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Returns a status string including battery information.
     *
     * @return a status description
     */
    public String status() {
        return getMake() + " " + getModel()
                + " | speed: " + speed
                + " km/h | battery: " + batteryLevel + "%";
    }
}
