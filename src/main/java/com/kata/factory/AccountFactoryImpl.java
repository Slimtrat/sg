package com.kata.factory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.kata.models.Account;

/**
 * Here, we will stored in memory, but could be in database
 */
public final class AccountFactoryImpl implements AccountFactory {
    private final Set<Account> bank;

    public AccountFactoryImpl() {
        bank = new HashSet<>();
        //
    }

    @Override
    public final String createAccount() {
        final Account account = new Account(); // would create an new account with 0 balance
        bank.add(account);
        return account.getId();
    }

    @Override
    public final Optional<Account> findAccount(final String id) {
        return bank//
                .stream()//
                .filter(acc -> id.equals(acc.getId()))//
                .findAny();
    }

    @Override
    public final void update(final Account account) {
        bank//
                .stream()//
                .filter(acc -> account.getId().equals(acc.getId()))//
                .forEach(acc -> account.setBalance(account.getBalance()));
    }
}
