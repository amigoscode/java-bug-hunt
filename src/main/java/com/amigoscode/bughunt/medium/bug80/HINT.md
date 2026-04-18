# Hint -- Bug 80

Look at how the permissions map is created in the constructor. What kind of map does `Collectors.toUnmodifiableMap` produce? What happens when you try to call `put()` or `remove()` on that map later?
