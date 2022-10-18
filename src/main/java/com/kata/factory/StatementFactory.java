package com.kata.factory;

import java.util.List;

import com.kata.entity.AccountStatement;
import com.kata.exception.AccountFactoryException;

/*
 * Here to represent action memory used in the project
 */
public interface StatementFactory {
    abstract void save(final int account, final AccountStatement statement);

    abstract List<AccountStatement> show(final int account) throws AccountFactoryException;
}
