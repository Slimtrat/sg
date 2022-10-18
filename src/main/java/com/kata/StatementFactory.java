package com.kata;

import java.util.List;

import com.kata.entity.Account;
import com.kata.entity.AccountStatement;

/*
 * Here to represent action memory used in the project
 */
interface StatementFactory {
    abstract void save(final Account account, final AccountStatement statement);

    abstract List<AccountStatement> show(final Account account);
}
