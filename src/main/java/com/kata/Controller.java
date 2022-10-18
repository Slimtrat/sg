package com.kata;

import java.util.List;

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
     * See all the AccountStatement linked to the account associated
     * 
     * @param accFrom
     * @return
     */
    abstract List<AccountStatement> accountStatement(final Account accFrom);
}
