# Hint -- Bug 83

Look at the validation condition in the compact constructor. The range is supposed to be 0 to 100 *inclusive*. Does the boundary check actually allow both endpoints?
