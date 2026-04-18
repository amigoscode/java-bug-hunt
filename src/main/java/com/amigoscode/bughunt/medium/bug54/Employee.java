package com.amigoscode.bughunt.medium.bug54;

import java.util.Objects;

public class Employee {

    private final String id;
    private final String name;
    private final double salary;

    public Employee(String id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public String role() {
        return "Employee";
    }

    public double annualBonus() {
        return salary * 0.05;
    }

    public String badge() {
        return id + " — " + name + " (" + role() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Employee other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return badge();
    }
}
