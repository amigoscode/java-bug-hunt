# Bug 44 — Off-by-one in iterative Fibonacci

**Bug:** With `a=0, b=1` and the loop running `n` times, each iteration advances one step. After `n` iterations we've computed fib(n+1) and stored it in `b`. So `compute(2)` returns `2`, not `1`.

**Fix — one iteration fewer:**
```java
for (int i = 0; i < n - 1; i++) { ... }
return b;
```
Or equivalently, start the loop at `i = 2`:
```java
for (int i = 2; i <= n; i++) { ... }
return b;
```

**Lesson:** Whenever you write a loop that "advances" state, write down what the variables *represent* at the start and end of each iteration. Here: after iteration `i`, `b` holds fib(i+2). That's one off from what we want.
