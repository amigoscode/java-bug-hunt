package com.amigoscode.bughunt.medium.bug79;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordPolicyTest {

    @Test
    void hasDigitShouldReturnTrueWhenPasswordContainsDigit() {
        PasswordPolicy policy = new PasswordPolicy(8);

        // "abc1def" clearly contains the digit '1'
        boolean result = policy.hasDigit("abc1def");

        assertThat(result).isTrue();
    }

    @Test
    void hasUppercaseShouldReturnTrueWhenPasswordContainsUppercase() {
        PasswordPolicy policy = new PasswordPolicy(8);

        // "helloWorld" contains 'W'
        boolean result = policy.hasUppercase("helloWorld");

        assertThat(result).isTrue();
    }

    @Test
    void isValidShouldReturnTrueForStrongPassword() {
        PasswordPolicy policy = new PasswordPolicy(8);

        // "MyP@ss1ng" has length >= 8, a digit, an uppercase letter, and a special char
        boolean result = policy.isValid("MyP@ss1ng");

        assertThat(result).isTrue();
    }

    @Test
    void hasDigitShouldReturnFalseWhenNoDigitsPresent() {
        PasswordPolicy policy = new PasswordPolicy(4);

        boolean result = policy.hasDigit("abcdef");

        assertThat(result).isFalse();
    }

    @Test
    void isLongEnoughShouldRespectMinLength() {
        PasswordPolicy policy = new PasswordPolicy(10);

        assertThat(policy.isLongEnough("short")).isFalse();
        assertThat(policy.isLongEnough("longenough")).isTrue();
    }
}
