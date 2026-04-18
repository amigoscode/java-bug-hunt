# Hint -- Bug 81

Both `Loggable` and `Auditable` define a `default String tag()` method. The class overrides `tag()` to resolve the diamond ambiguity -- but look carefully at which interface's version it delegates to. Is it combining both, or silently dropping one?
