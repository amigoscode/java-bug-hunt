package com.amigoscode.bughunt.medium.bug54;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Manager extends Employee {

    private final String department;
    private final List<Employee> directReports;

    public Manager(String id, String name, double salary, String department) {
        super(id, name, salary);
        this.department = department;
        this.directReports = new ArrayList<>();
    }

    public String getDepartment() {
        return department;
    }

    public void addReport(Employee employee) {
        directReports.add(employee);
    }

    public List<Employee> getDirectReports() {
        return Collections.unmodifiableList(directReports);
    }

    public int teamSize() {
        return directReports.size();
    }

    @Override
    public String role() {
        return "Manager";
    }

    @Override
    public double annualBonus() {
        return getSalary() * 0.10 + teamSize() * 500;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Manager manager = (Manager) o;
        return Objects.equals(department, manager.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), department);
    }

    public String teamReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Manager: ").append(getName()).append(" (").append(department).append(")\n");
        sb.append("Team size: ").append(teamSize()).append("\n");
        for (Employee e : directReports) {
            sb.append("  - ").append(e.badge()).append("\n");
        }
        return sb.toString();
    }
}
