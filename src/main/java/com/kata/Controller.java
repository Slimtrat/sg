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
    abstract String createAccount() throws ControllerException;

    /**
     * Deposit an ammount of money on the account associated
     * 
     * @param acc
     * @param amount
     */
    abstract void deposit(final String acc, final float amount) throws ControllerException;

    /**
     * withdraw an ammount of money on the account associated
     * 
     * @param acc
     * @param amount
     */
    abstract void withdraw(final String accFrom, final float amount) throws ControllerException;

    /**
     * get the balance of the account
     * 
     * @param acc
     */
    abstract float getBalance(final String accFrom) throws ControllerException;

    /**
     * See all the AccountStatement linked to the account associated
     * 
     * @param accFrom
     * @return
     */
    abstract List<AccountStatement> accountStatement(final String accFrom) throws ControllerException;
}
