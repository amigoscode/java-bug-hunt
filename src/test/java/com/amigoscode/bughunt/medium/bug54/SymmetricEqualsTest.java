package com.amigoscode.bughunt.medium.bug54;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SymmetricEqualsTest {

    @Test
    void equalsIsSymmetricBetweenEmployeeAndManager() {
        Employee employee = new Employee("E1", "Alice", 80000);
        Manager manager = new Manager("E1", "Alice", 95000, "Engineering");

        // Symmetry: if a.equals(b) then b.equals(a)
        boolean employeeEqualsManager = employee.equals(manager);
        boolean managerEqualsEmployee = manager.equals(employee);

        assertThat(employeeEqualsManager).isEqualTo(managerEqualsEmployee);
    }

    @Test
    void twoManagersSameDepartmentAreEqual() {
        Manager m1 = new Manager("M1", "Bob", 100000, "Sales");
        Manager m2 = new Manager("M1", "Bob", 110000, "Sales");

        assertThat(m1.equals(m2)).isTrue();
        assertThat(m2.equals(m1)).isTrue();
    }

    @Test
    void twoManagersDifferentDepartmentAreNotEqual() {
        Manager m1 = new Manager("M1", "Bob", 100000, "Sales");
        Manager m2 = new Manager("M1", "Bob", 100000, "Engineering");

        assertThat(m1.equals(m2)).isFalse();
    }
}
