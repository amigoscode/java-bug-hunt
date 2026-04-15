# Hint — Bug 46

Run the test. What exception do you get — and from which line?

Think about the order of events when `new Dog("labrador")` runs: super constructor first, then the subclass body. When the super constructor calls `computeSound()`, has `breed` been assigned yet?
