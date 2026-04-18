package com.amigoscode.bughunt.medium.bug79;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Validates passwords against a configurable set of policy rules:
 * minimum length, at least one digit, at least one uppercase letter,
 * and at least one special character.
 *
 * BUG: The digit and uppercase checks use {@code Pattern.matcher(...).matches()},
 * which requires the <em>entire</em> string to match the pattern. Since the
 * patterns are single-character classes (e.g. {@code \\d}), they only match
 * strings that are exactly one digit long. The correct method is
 * {@code .find()}, which searches for a match anywhere in the string.
 */
public class PasswordPolicy {

    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
    private static final Pattern UPPER_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern SPECIAL_PATTERN = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]");

    private final int minLength;

    /**
     * Creates a policy with the given minimum password length.
     *
     * @param minLength the minimum number of characters required
     * @throws IllegalArgumentException if minLength is less than 1
     */
    public PasswordPolicy(int minLength) {
        if (minLength < 1) {
            throw new IllegalArgumentException("minLength must be at least 1");
        }
        this.minLength = minLength;
    }

    /**
     * Checks whether the password meets the minimum length requirement.
     *
     * @param password the password to check
     * @return true if the password is long enough
     */
    public boolean isLongEnough(String password) {
        Objects.requireNonNull(password, "password must not be null");
        return password.length() >= minLength;
    }

    /**
     * Checks whether the password contains at least one digit.
     * <p>
     * BUG: uses {@code matches()} instead of {@code find()}.
     * {@code matches()} requires the entire string to match the regex
     * {@code \\d}, so it only returns true for single-digit strings.
     *
     * @param password the password to check
     * @return true if the password contains a digit (buggy: only true for single-digit strings)
     */
    public boolean hasDigit(String password) {
        Objects.requireNonNull(password, "password must not be null");
        // BUG: matches() requires full match -- "abc1def" does NOT match "\\d"
        return DIGIT_PATTERN.matcher(password).matches();
    }

    /**
     * Checks whether the password contains at least one uppercase letter.
     * <p>
     * BUG: same issue as {@link #hasDigit(String)} -- uses matches()
     * instead of find().
     *
     * @param password the password to check
     * @return true if the password contains an uppercase letter (buggy)
     */
    public boolean hasUppercase(String password) {
        Objects.requireNonNull(password, "password must not be null");
        // BUG: matches() requires full match
        return UPPER_PATTERN.matcher(password).matches();
    }

    /**
     * Checks whether the password contains at least one special character.
     * This method is implemented correctly using {@code find()}.
     *
     * @param password the password to check
     * @return true if the password contains a special character
     */
    public boolean hasSpecialChar(String password) {
        Objects.requireNonNull(password, "password must not be null");
        return SPECIAL_PATTERN.matcher(password).find();
    }

    /**
     * Validates a password against all policy rules.
     *
     * @param password the password to validate
     * @return true if the password passes all checks
     */
    public boolean isValid(String password) {
        return isLongEnough(password)
                && hasDigit(password)
                && hasUppercase(password)
                && hasSpecialChar(password);
    }

    /**
     * Returns the minimum length requirement.
     */
    public int minLength() {
        return minLength;
    }

    @Override
    public String toString() {
        return "PasswordPolicy{minLength=" + minLength + "}";
    }
}
