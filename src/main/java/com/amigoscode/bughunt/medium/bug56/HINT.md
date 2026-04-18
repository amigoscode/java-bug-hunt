# Hint — Bug 56

Look at how `formatEntry` converts the `message` and `source` fields to strings. What happens when one of them is `null`? Is there a difference between `obj.toString()` and `String.valueOf(obj)`?
