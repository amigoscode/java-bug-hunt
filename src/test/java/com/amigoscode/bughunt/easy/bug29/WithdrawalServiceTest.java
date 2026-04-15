package com.amigoscode.bughunt.easy.bug29;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WithdrawalServiceTest {

    @Test
    void successfulWithdrawalReturnsZero() {
        WithdrawalService svc = new WithdrawalService("A1", 1000);

        assertThat(svc.attemptWithdrawal(400)).isEqualTo(0);
        assertThat(svc.balance()).isEqualTo(600);
    }

    @Test
    void zeroAmountReturnsMinusOne() {
        WithdrawalService svc = new WithdrawalService("A1", 1000);

        assertThat(svc.attemptWithdrawal(0)).isEqualTo(-1);
    }

    @Test
    void overdraftReturnsMinusTwo() {
        WithdrawalService svc = new WithdrawalService("A1", 100);

        assertThat(svc.attemptWithdrawal(500)).isEqualTo(-2);
        assertThat(svc.balance()).isEqualTo(100);
    }
}
