package com.amigoscode.bughunt.easy.bug13;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountLockTest {

    @Test
    void allowsAccessWhenUnlocked() {
        AccountLock lock = new AccountLock();
        assertThat(lock.attemptAccess("ada", "secret")).isTrue();
    }

    @Test
    void rejectsAccessWhenLocked() {
        AccountLock lock = new AccountLock();
        lock.lock();
        assertThat(lock.attemptAccess("ada", "secret")).isFalse();
    }

    @Test
    void unlockAllowsAccessAgain() {
        AccountLock lock = new AccountLock();
        lock.lock();
        lock.unlock();
        assertThat(lock.attemptAccess("ada", "secret")).isTrue();
        assertThat(lock.isLocked()).isFalse();
    }
}
