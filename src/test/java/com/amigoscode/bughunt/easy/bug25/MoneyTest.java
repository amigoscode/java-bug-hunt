package com.amigoscode.bughunt.easy.bug25;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {

    @Test
    void equalMoneyInstances() {
        Money a = new Money(100, "USD");
        Money b = new Money(100, "USD");

        assertThat(a).isEqualTo(b);
    }

    @Test
    void differentAmountsAreNotEqual() {
        Money a = new Money(100, "USD");
        Money b = new Money(200, "USD");

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void setTreatsEqualMoneyAsDuplicate() {
        Set<Money> set = new HashSet<>();
        set.add(new Money(100, "USD"));
        set.add(new Money(100, "USD"));

        assertThat(set).hasSize(1);
    }
}
