package com.amigoscode.bughunt.medium.bug97;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElectricVehicleTest {

    @Test
    void describeShouldReflectCurrentSpeedAfterAcceleration() {
        ElectricVehicle ev = new ElectricVehicle("Tesla", "Model 3", 200);

        ev.accelerate(80);

        // describe() is inherited from Vehicle and reads Vehicle.speed
        // BUG: Vehicle.speed is still 0 because ElectricVehicle shadows it
        assertThat(ev.describe()).isEqualTo("Tesla Model 3 going at 80 km/h");
    }

    @Test
    void getSpeedShouldReturnCurrentSpeedAfterAcceleration() {
        ElectricVehicle ev = new ElectricVehicle("Rivian", "R1T", 180);

        ev.accelerate(60);

        // getSpeed() is inherited from Vehicle and reads Vehicle.speed
        // BUG: Vehicle.speed is still 0
        assertThat(ev.getSpeed()).isEqualTo(60);
    }

    @Test
    void accelerateShouldNotExceedMaxSpeed() {
        ElectricVehicle ev = new ElectricVehicle("Nissan", "Leaf", 150);

        ev.accelerate(200);

        // The status method reads the child's speed field, so this would pass
        // But getSpeed reads the parent's speed field
        assertThat(ev.getSpeed()).isEqualTo(150);
    }

    @Test
    void brakeShouldReduceSpeedReportedByDescribe() {
        ElectricVehicle ev = new ElectricVehicle("BMW", "iX", 200);

        ev.accelerate(100);
        ev.brake(30);

        // describe() reads parent's speed, which is always 0
        assertThat(ev.describe()).contains("70 km/h");
    }

    @Test
    void batteryLevelShouldDecreaseAfterAcceleration() {
        ElectricVehicle ev = new ElectricVehicle("Tesla", "Model S", 250);

        ev.accelerate(50);

        assertThat(ev.getBatteryLevel()).isLessThan(100);
    }
}
