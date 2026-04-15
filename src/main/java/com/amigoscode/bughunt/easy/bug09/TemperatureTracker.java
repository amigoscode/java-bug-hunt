package com.amigoscode.bughunt.easy.bug09;

public class TemperatureTracker {

    private int min = 0;
    private int max = 0;

    public void record(int temperature) {
        if (temperature < min) {
            min = temperature;
        }
        if (temperature > max) {
            max = temperature;
        }
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }
}
