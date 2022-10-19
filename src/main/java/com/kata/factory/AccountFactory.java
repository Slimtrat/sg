package com.kata.factory;

import java.util.Optional;

import com.kata.exception.AccountFactoryException;
import com.kata.models.Account;

public interface AccountFactory {
    /**
     * Create an account an return its id
     * 
     * @return
     */
    public abstract String createAccount() throws AccountFactoryException;

    /**
     * find an account with its id
     * 
     * @param id
     * @return
     */
    public abstract Optional<Account> findAccount(final String id) throws AccountFactoryException;

    /**
     * MUST be used THREADSAFE
     * 
     * @param account
     * @throws AccountFactoryException
     */
    public abstract void update(Account account) throws AccountFactoryException;
}
