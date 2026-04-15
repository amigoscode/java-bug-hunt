# Hint — Bug 37

The constructor stores the `tags` list. The second test mutates the *original* list the caller passed in. Does that change what the profile sees?

Where does ownership transfer happen?
