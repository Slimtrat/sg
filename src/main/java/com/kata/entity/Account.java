package com.kata.entity;

import com.kata.MyException;

/*
 * Represent any account 
 */
public final class Account {
    private float balance;

    public Account() {
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
}
