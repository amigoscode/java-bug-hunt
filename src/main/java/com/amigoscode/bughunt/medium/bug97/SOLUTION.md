# Bug 97 -- Shadowed field in subclass

**Bug:** `ElectricVehicle` declares its own `private int speed` field, which shadows the parent `Vehicle`'s `protected int speed`. When `ElectricVehicle.accelerate()` writes to `speed`, it updates the child's field. But `Vehicle.describe()` and `Vehicle.getSpeed()` read the parent's field, which remains at 0.

```java
// Buggy -- ElectricVehicle:
private int speed;  // shadows Vehicle.speed

@Override
public void accelerate(int amount) {
    speed = Math.min(speed + amount, maxSpeed);  // writes child's speed
}

// Vehicle.describe() reads Vehicle.speed (always 0):
public String describe() {
    return make + " " + model + " going at " + speed + " km/h";
}
```

**Fix:** Remove the `speed` field declaration from `ElectricVehicle` and use the inherited `protected int speed` from `Vehicle`:

```java
// Fixed -- ElectricVehicle (remove the field declaration):
// private int speed;  <-- DELETE THIS LINE

@Override
public void accelerate(int amount) {
    speed = Math.min(speed + amount, maxSpeed);  // now writes parent's field
}
```

**Lesson:** In Java, fields are not polymorphic -- they are resolved based on the reference type at compile time, not the runtime type. Declaring a field in a subclass with the same name as a parent field creates two separate fields. The parent's methods always access the parent's field. This is called "field shadowing" and is almost always a bug. Use `@Override` methods instead of shadowed fields, or simply use the inherited field directly.
