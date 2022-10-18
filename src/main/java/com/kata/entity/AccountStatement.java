package com.kata.entity;

import java.util.Calendar;
import java.util.Date;

/**
 * Represent any account action with the app
 */
public final class AccountStatement {
    private static final Calendar CAL = Calendar.getInstance();
    private final Date date;
    private final float amount;
    private final float balance;

    public AccountStatement(final Date date, final float amount, final float balance) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public AccountStatement(final float amount, final float balance) {
        this.date = CAL.getTime();
        this.amount = amount;
        this.balance = balance;
    }
}
