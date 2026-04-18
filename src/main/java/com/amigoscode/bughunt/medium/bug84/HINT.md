# Hint -- Bug 84

Look at the difference between `Optional.orElse(value)` and `Optional.orElseGet(supplier)`. One of them evaluates its argument eagerly -- regardless of whether the Optional is present. What side effect does that cause here?
