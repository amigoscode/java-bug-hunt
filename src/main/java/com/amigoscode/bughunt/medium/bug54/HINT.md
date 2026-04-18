# Hint — Bug 54

The `equals` contract requires symmetry: `a.equals(b)` must equal `b.equals(a)`. Look at what `Employee.equals` checks versus what `Manager.equals` checks — particularly the `getClass()` vs `instanceof` difference.
