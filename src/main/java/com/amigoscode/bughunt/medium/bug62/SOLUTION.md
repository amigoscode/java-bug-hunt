# Bug 62 — Catch-all Exception swallows specific error types

**Bug:** The `processPayment` method catches `Exception` broadly and always returns `ERROR_UNKNOWN (-1)`, regardless of whether the cause was bad input (`IllegalArgumentException`), insufficient funds (`InsufficientFundsException`), or fraud (`FraudDetectedException`). The caller receives no useful information about what went wrong.

**Fix:** Catch specific exception types first, then fall back to generic:

```java
public int processPayment(double amount) {
    try {
        validate(amount);
        checkFraud(amount);
        charge(amount);
        transactionLog.add("SUCCESS: " + amount);
        return SUCCESS;
    } catch (IllegalArgumentException e) {
        transactionLog.add("FAILED: " + amount + " reason=" + e.getMessage());
        return ERROR_INVALID_AMOUNT;
    } catch (InsufficientFundsException e) {
        transactionLog.add("FAILED: " + amount + " reason=" + e.getMessage());
        return ERROR_INSUFFICIENT_FUNDS;
    } catch (FraudDetectedException e) {
        transactionLog.add("FAILED: " + amount + " reason=" + e.getMessage());
        return ERROR_FRAUD_DETECTED;
    } catch (Exception e) {
        transactionLog.add("FAILED: " + amount + " reason=" + e.getMessage());
        return ERROR_UNKNOWN;
    }
}
```

**Lesson:** Catching `Exception` (or `Throwable`) as a blanket handler is a common anti-pattern that hides the root cause of errors. Always catch specific exceptions first and handle each appropriately. A generic catch-all should only be used as a last-resort fallback, not as the primary error handler.
