package com.kata.factory;

import java.util.List;

import com.kata.exception.StatementFactoryException;
import com.kata.models.AccountStatement;

/*
 * Here to represent action memory used in the project
 */
public interface StatementFactory {
    abstract void save(final String account, final AccountStatement statement) throws StatementFactoryException;

    abstract List<AccountStatement> show(final String account) throws StatementFactoryException;
}
