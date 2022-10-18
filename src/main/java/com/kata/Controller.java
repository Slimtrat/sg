package com.kata;

import java.util.List;

import com.kata.entity.Account;
import com.kata.entity.AccountStatement;

/*
 * Control every possible operation
 */
public interface Controller {
    /**
     * Deposit an ammount of money on the account associated
     * 
     * @param acc
     * @param amount
     */
    abstract void deposit(final Account acc, final float amount);

    /**
     * withdraw an ammount of money on the account associated
     * 
     * @param acc
     * @param amount
     */
    abstract void withdraw(final Account accFrom, final float amount);

    /**
     * get the balance of the account
     * 
     * @param acc
     */
    abstract int getBalance(final Account accFrom);

    /**
     * See all the AccountStatement linked to the account associated
     * 
     * @param accFrom
     * @return
     */
    abstract List<AccountStatement> accountStatement(final Account accFrom);
}
