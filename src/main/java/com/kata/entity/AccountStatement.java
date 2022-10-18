package com.kata.entity;

import java.util.Calendar;
import java.util.Date;

import com.kata.ActionEnum;

/**
 * Represent any account action with the app
 */
public final class AccountStatement {
    private static final String T_STRING = "[%s] %s %s";
    private static final String T_STRING_AMOUNT = "[%s] %s %s of %d";

    private final Calendar cal = Calendar.getInstance();
    private final Date date;
    private final float amount;
    private final ActionEnum action;
    private boolean successfull = true;

    public AccountStatement(final ActionEnum action, final float amount) {
        this.date = cal.getTime();
        this.amount = amount;
        this.action = action;
    }

    /**
     * For show action
     * 
     * @param action
     */
    public AccountStatement(final ActionEnum action) {
        this.date = cal.getTime();
        this.amount = 0;
        this.action = action;
    }

    public final void hasFail() {
        this.successfull = false;
    }

    @Override
    public final String toString() {
        final String hasFailed = this.successfull ? "OK" : "NOK";
        if (ActionEnum.SHOW.equals(this.action) || ActionEnum.CREATE.equals(this.action)) {
            return String.format(T_STRING, this.date.toString(), this.action.name(), hasFailed);
        } else {
            return String.format(T_STRING_AMOUNT, this.date.toString(), this.action.name(), hasFailed, this.amount);
        }
    }
}
