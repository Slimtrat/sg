package com.kata;

import java.util.Date;

/**
 * Represent any account action with the app
 */
public final class AccountStatement {
    private final Date date;
    private final float amount;
    private final float balance;

    public AccountStatement(final Date date, final float amount, final float balance) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }
}
