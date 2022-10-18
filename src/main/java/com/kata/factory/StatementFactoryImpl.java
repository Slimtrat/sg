package com.kata.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kata.entity.AccountStatement;
import com.kata.exception.AccountFactoryException;

/**
 * Responsible for managing statement storage
 * Here, we will stored in memory, but could be in database
 */
public final class StatementFactoryImpl implements StatementFactory {

    private final Map<Integer, List<AccountStatement>> store;

    public StatementFactoryImpl() {
        store = new HashMap<>();
    }

    @Override
    public final void save(final int accountId, final AccountStatement statement) {
        store.computeIfAbsent(accountId, ArrayList::new).add(statement); // TODO not sure
    }

    @Override
    public final List<AccountStatement> show(final int accountId) throws AccountFactoryException {
        if (!store.containsKey(accountId)) {
            throw new AccountFactoryException("Unknow account id"); // even if it is just created, it would be known
                                                                    // because creation make a log
        }
        return store//
                .get(accountId);
    }
}