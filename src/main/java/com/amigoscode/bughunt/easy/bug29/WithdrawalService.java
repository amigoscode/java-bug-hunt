package com.amigoscode.bughunt.easy.bug29;

public class WithdrawalService {

    private long balanceCents;
    private final String accountId;

    public WithdrawalService(String accountId, long openingBalanceCents) {
        this.accountId = accountId;
        this.balanceCents = openingBalanceCents;
    }

    public long balance() {
        return balanceCents;
    }

    public int attemptWithdrawal(long amountCents) {
        try {
            if (amountCents <= 0) {
                return -1;
            }
            if (amountCents > balanceCents) {
                return -2;
            }
            balanceCents -= amountCents;
            return 0;
        } finally {
            return 99;
        }
    }

    public String statement() {
        return accountId + ": " + balanceCents + " cents";
    }
}
