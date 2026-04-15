# Bug 47 — Loop runs twice, undoing itself

**Bug:** The loop iterates over *all* indices. When `i=0` swaps with `j=3`, and then `i=3` swaps with `j=0`, the two swaps cancel. The array ends up unchanged (even-length) or partially wrong.

**Fix:** Iterate only the first half:
```java
for (int i = 0; i < arr.length / 2; i++) {
    int j = arr.length - 1 - i;
    swap(arr, i, j);
}
```
Or use two converging pointers:
```java
int i = 0, j = arr.length - 1;
while (i < j) {
    swap(arr, i++, j--);
}
```

**Lesson:** When an operation is symmetric (swap, XOR toggle, …), doing it twice undoes it. Iterate only over the "first half" to avoid double-application. Same trap shows up in "toggle a boolean for every element matching X" when the collection itself overlaps the update.
