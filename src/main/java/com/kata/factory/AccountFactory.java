package com.kata.factory;

import java.util.Optional;

import com.kata.entity.Account;
import com.kata.exception.AccountFactoryException;

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

    public abstract void update(Account account) throws AccountFactoryException;

}
