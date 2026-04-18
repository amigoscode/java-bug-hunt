package com.amigoscode.bughunt.medium.bug62;

import java.util.ArrayList;
import java.util.List;

/**
 * Processes payments with validation, fraud checks, and charging.
 * Returns status codes indicating the outcome of each step.
 */
public class PaymentProcessor {

    public static final int SUCCESS = 0;
    public static final int ERROR_INVALID_AMOUNT = -2;
    public static final int ERROR_INSUFFICIENT_FUNDS = -3;
    public static final int ERROR_FRAUD_DETECTED = -4;
    public static final int ERROR_UNKNOWN = -1;

    private final double accountBalance;
    private final List<String> transactionLog = new ArrayList<>();

    public PaymentProcessor(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Processes a payment for the given amount.
     * Returns a status code indicating the result.
     */
    public int processPayment(double amount) {
        try {
            validate(amount);
            checkFraud(amount);
            charge(amount);
            transactionLog.add("SUCCESS: " + amount);
            return SUCCESS;
        } catch (Exception e) {
            // BUG: catches all exceptions with a generic error code.
            // IllegalArgumentException (bad input) should return ERROR_INVALID_AMOUNT (-2)
            // InsufficientFundsException should return ERROR_INSUFFICIENT_FUNDS (-3)
            // FraudDetectedException should return ERROR_FRAUD_DETECTED (-4)
            transactionLog.add("FAILED: " + amount + " reason=" + e.getMessage());
            return ERROR_UNKNOWN;
        }
    }

    /**
     * Validates that the payment amount is acceptable.
     *
     * @throws IllegalArgumentException if amount is invalid
     */
    private void validate(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > 1_000_000) {
            throw new IllegalArgumentException("Amount exceeds maximum allowed");
        }
    }

    /**
     * Checks for potential fraud indicators.
     *
     * @throws FraudDetectedException if the transaction looks fraudulent
     */
    private void checkFraud(double amount) {
        if (amount == 99_999.99) {
            throw new FraudDetectedException("Suspicious round amount pattern");
        }
    }

    /**
     * Charges the given amount against the account balance.
     *
     * @throws InsufficientFundsException if balance is too low
     */
    private void charge(double amount) {
        if (amount > accountBalance) {
            throw new InsufficientFundsException("Balance " + accountBalance + " < " + amount);
        }
    }

    /**
     * Returns a copy of the transaction log.
     */
    public List<String> getTransactionLog() {
        return new ArrayList<>(transactionLog);
    }

    /**
     * Returns the number of transactions processed (success or failure).
     */
    public int transactionCount() {
        return transactionLog.size();
    }

    public static class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    public static class FraudDetectedException extends RuntimeException {
        public FraudDetectedException(String message) {
            super(message);
        }
    }
}
