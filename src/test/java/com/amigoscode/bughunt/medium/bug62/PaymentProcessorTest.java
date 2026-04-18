package com.amigoscode.bughunt.medium.bug62;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentProcessorTest {

    @Test
    void negativeAmountReturnsInvalidAmountError() {
        PaymentProcessor processor = new PaymentProcessor(1000.0);

        int result = processor.processPayment(-50.0);

        // Negative amount should be detected as bad input, not generic error
        assertThat(result).isEqualTo(PaymentProcessor.ERROR_INVALID_AMOUNT);
    }

    @Test
    void excessiveAmountReturnsInvalidAmountError() {
        PaymentProcessor processor = new PaymentProcessor(500_000.0);

        int result = processor.processPayment(2_000_000.0);

        assertThat(result).isEqualTo(PaymentProcessor.ERROR_INVALID_AMOUNT);
    }

    @Test
    void insufficientFundsReturnsCorrectError() {
        PaymentProcessor processor = new PaymentProcessor(100.0);

        int result = processor.processPayment(500.0);

        // Over-balance should return insufficient funds, not generic error
        assertThat(result).isEqualTo(PaymentProcessor.ERROR_INSUFFICIENT_FUNDS);
    }

    @Test
    void fraudulentAmountReturnsFraudError() {
        PaymentProcessor processor = new PaymentProcessor(200_000.0);

        int result = processor.processPayment(99_999.99);

        assertThat(result).isEqualTo(PaymentProcessor.ERROR_FRAUD_DETECTED);
    }

    @Test
    void validPaymentReturnsSuccess() {
        PaymentProcessor processor = new PaymentProcessor(1000.0);

        int result = processor.processPayment(250.0);

        assertThat(result).isEqualTo(PaymentProcessor.SUCCESS);
    }

    @Test
    void transactionLogRecordsAllAttempts() {
        PaymentProcessor processor = new PaymentProcessor(1000.0);

        processor.processPayment(250.0);
        processor.processPayment(-10.0);

        assertThat(processor.transactionCount()).isEqualTo(2);
        assertThat(processor.getTransactionLog().get(0)).startsWith("SUCCESS");
        assertThat(processor.getTransactionLog().get(1)).startsWith("FAILED");
    }
}
