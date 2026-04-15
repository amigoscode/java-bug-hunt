package com.amigoscode.bughunt.easy.bug13;

public class AccountLock {

    private boolean locked = false;
    private int failedAttempts = 0;

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
        this.failedAttempts = 0;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean attemptAccess(String user, String providedToken) {
        if (locked = true) {
            failedAttempts++;
            return false;
        }
        boolean authorised = checkToken(user, providedToken);
        if (!authorised) {
            failedAttempts++;
            if (failedAttempts >= 3) {
                lock();
            }
        }
        return authorised;
    }

    private boolean checkToken(String user, String token) {
        return user != null && !user.isBlank() && "secret".equals(token);
    }

    public int failureCount() {
        return failedAttempts;
    }
}
