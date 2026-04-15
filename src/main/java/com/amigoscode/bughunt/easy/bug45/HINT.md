# Hint — Bug 45

Trace `power(2, 3)`. It recurses to `power(2, 1)`, which returns 2. Then `half * half = 4`. But `2^3 = 8`.

What happens when `exponent` is odd? Is `exponent/2 + exponent/2` equal to `exponent`?
