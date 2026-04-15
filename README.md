# Java Bug Hunt 🐛

150 intentionally buggy Java programs for students to hunt, diagnose, and fix.

## Structure

```
src/main/java/com/amigoscode/bughunt/
  easy/bugNN/     (50 bugs)
  medium/bugNN/   (50 bugs)
  hard/bugNN/     (50 bugs)

src/test/java/com/amigoscode/bughunt/
  easy/bugNN/     JUnit 5 + AssertJ + Mockito tests (currently FAILING)
```

## How to play

1. Pick a bug directory, e.g. `easy/bug01`
2. Read `HINT.md`
3. Run the tests for that bug:
   ```bash
   mvn test -Dtest="Bug01*"
   ```
4. Fix the code in `src/main/...` until the tests pass
5. Compare your fix with `SOLUTION.md`

## Run all tests

```bash
mvn test
```

## Tiers

- **Easy (1–50)** — syntax & basic logic traps
- **Medium (51–100)** — API & OOP misuse
- **Hard (101–150)** — subtle semantics, generics, memory, numerics

## Requirements

- Java 21
- Maven 3.9+
