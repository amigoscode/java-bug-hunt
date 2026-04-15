# Bug 46 — Constructor calling an overridable method

**Bug:** `Animal`'s constructor calls `computeSound()`, which is overridden in `Dog`. But Java's initialization order is:

1. `super("dog")` runs — inside it, `computeSound()` is dispatched virtually to `Dog.computeSound`
2. `Dog.computeSound` tries `breed.toUpperCase()` — but `breed` is still `null` because the subclass's field assignment hasn't happened yet.

Result: `NullPointerException`.

**Fix options:**
1. Don't call overridable methods from constructors. Make `computeSound` `private`, `static`, or `final`, or inline the logic.
2. Pass the computed value in:
   ```java
   public Animal(String species, String sound) {
       this.species = species;
       this.sound = sound;
   }
   public Dog(String breed) {
       super("dog", breed.toUpperCase() + " bark!");
       this.breed = breed;
   }
   ```

**Lesson:** Never call overridable (non-final, non-private, non-static) methods from a constructor. Subclass fields don't exist yet. This rule appears in *Effective Java* (Item 19) for exactly this reason.
