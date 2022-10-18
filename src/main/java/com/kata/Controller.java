package com.kata;

import java.util.List;

import com.kata.entity.AccountStatement;
import com.kata.exception.ControllerException;

/*
 * Control every possible operation
 */
public interface Controller {
    /*
     * Create an account and return its id
     */
    abstract int createAccount() throws ControllerException;

    /**
     * Deposit an ammount of money on the account associated
     * 
     * @param acc
     * @param amount
     */
    abstract void deposit(final int acc, final float amount) throws ControllerException;

    /**
     * withdraw an ammount of money on the account associated
     * 
     * @param acc
     * @param amount
     */
    abstract void withdraw(final int accFrom, final float amount) throws ControllerException;

    /**
     * get the balance of the account
     * 
     * @param acc
     */
    abstract float getBalance(final int accFrom) throws ControllerException;

    /**
     * See all the AccountStatement linked to the account associated
     * 
     * @param accFrom
     * @return
     */
    abstract List<AccountStatement> accountStatement(final int accFrom) throws ControllerException;
}
