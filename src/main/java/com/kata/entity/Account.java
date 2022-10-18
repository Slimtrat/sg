package com.kata.entity;

import java.util.concurrent.atomic.LongAdder;

import com.kata.exception.MyException;

/*
 * Represent any account 
 */
public final class Account {
    private static final LongAdder NB_ACCOUNT = new LongAdder();
    private final int id;
    private float balance;

    public Account() {
        NB_ACCOUNT.increment();
        this.id = NB_ACCOUNT.intValue();
        this.balance = 0;
    }

    public final void deposit(final float amount) {
        this.balance += amount;
    }

    public final void withdraw(final float amount) throws MyException {
        if (this.balance < amount) {
            throw new MyException("Amount too high");
        }
        this.balance -= amount;
    }

    public final float getBalance() {
        return this.balance;
    }

    public final void setBalance(final float balance) {
        this.balance = balance;
    }

    /**
     * Here cannot be really implemented, account are too simple
     */
    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    /**
     * compare the id
     */
    @Override
    public final boolean equals(Object obj) {
        return (obj instanceof Account) && ((Account) obj).id == this.id;
    }

    public final int getId() {
        return this.id;
    }
}
