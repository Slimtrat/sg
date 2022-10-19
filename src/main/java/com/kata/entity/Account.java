package com.kata.entity;

import java.util.UUID;

import com.kata.exception.MyException;

/*
 * Represent any account 
 */
public final class Account {
    private final String guid;
    private float balance;

    public Account() {
        this.guid = UUID.randomUUID().toString();
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
    public final boolean equals(final Object obj) {
        return (obj instanceof Account) && ((Account) obj).guid == this.guid;
    }

    public final String getId() {
        return this.guid;
    }
}
