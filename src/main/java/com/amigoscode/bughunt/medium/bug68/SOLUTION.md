# Bug 68 -- Iterable returns itself as Iterator (shared cursor)

**Bug:** `NumberSequence` implements both `Iterable<Integer>` and `Iterator<Integer>`, and its `iterator()` method returns `this`. This means every call to `iterator()` returns the same object with the same `current` counter. After the first iteration completes, `current` equals `max`, and `hasNext()` returns `false` immediately. All subsequent iterations produce zero elements.

```java
// Buggy:
@Override
public Iterator<Integer> iterator() {
    return this; // same cursor, never reset
}
```

**Fix:** Return a new iterator instance each time `iterator()` is called:

```java
@Override
public Iterator<Integer> iterator() {
    return new Iterator<>() {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < max;
        }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return cursor++;
        }
    };
}
```

And remove `implements Iterator<Integer>` from the class declaration.

**Lesson:** An `Iterable` must be able to produce multiple independent iterators. Implementing both `Iterable` and `Iterator` on the same class and returning `this` from `iterator()` makes the object single-use. The enhanced for-loop calls `iterator()` each time, but if it always gets the same exhausted iterator, subsequent loops silently produce nothing.
