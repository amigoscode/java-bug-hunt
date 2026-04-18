# Bug 99 -- Static method hiding (not overriding)

**Bug:** `Formatter.format()` and `Formatter.formatBlock()` are `static` methods. `HtmlFormatter` declares its own `static` methods with the same signatures. This is called "method hiding," not overriding. When you call `format()` through a `Formatter` reference (even if it points to an `HtmlFormatter` object), Java resolves the call at compile time to `Formatter.format()` based on the reference type.

```java
// Buggy:
Formatter f = new HtmlFormatter("web", true);
f.format("hello");  // calls Formatter.format(), returns "hello" (not "<p>hello</p>")
```

**Fix:** Make the methods non-static (instance methods) so they can be properly overridden:

```java
// Fixed -- Formatter:
public String format(String input) {  // remove 'static'
    return input;
}

// Fixed -- HtmlFormatter:
@Override
public String format(String input) {  // remove 'static', add @Override
    return "<p>" + input + "</p>";
}
```

**Lesson:** In Java, `static` methods belong to the class, not the instance. They cannot participate in runtime polymorphism. When a subclass defines a static method with the same signature as a parent class, it *hides* (not overrides) the parent's method. The method that runs depends on the reference type at compile time, not the runtime type of the object. Always use instance methods when you want polymorphic behavior.
