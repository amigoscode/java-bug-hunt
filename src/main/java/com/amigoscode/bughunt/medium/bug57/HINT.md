# Hint — Bug 57

Look at the `get` method. It accepts a `Class<T> type` parameter — but does it actually *use* it? What happens to a generic cast `(T)` after type erasure?
