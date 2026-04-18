# Hint -- Bug 87

Look carefully at the `removeByPrefix` method. The `Iterator` contract says you must call `next()` before each call to `remove()`. What is the method actually calling before `remove()`? Is the current element ever being retrieved?
