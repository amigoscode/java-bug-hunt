# Hint -- Bug 78

Records in Java are meant to be immutable. But what happens when one of the record's components is a mutable type like `ArrayList`? Does the generated accessor return a copy or the original reference?
