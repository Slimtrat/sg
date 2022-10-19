package com.kata.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kata.entity.AccountStatement;
import com.kata.exception.StatementFactoryException;

/**
 * Responsible for managing statement storage
 * Here, we will stored in memory, but could be in database
 */
public final class StatementFactoryImpl implements StatementFactory {

    private final Map<String, List<AccountStatement>> store;

    public StatementFactoryImpl() {
        store = new HashMap<>();
    }

    @Override
    public final void save(final String accountId, final AccountStatement statement) throws StatementFactoryException {
        try {
            store.computeIfAbsent(accountId, x -> new ArrayList<>()).add(statement);
        } catch (final Exception e) {
            throw new StatementFactoryException("cannot save it");
        }
    }

    @Override
    public final List<AccountStatement> show(final String accountId) throws StatementFactoryException {
        if (!store.containsKey(accountId)) {
            throw new StatementFactoryException("Unknow account id");
            // even if it is just created, it would be known because creation make a log
        }
        try {
            return store//
                    .get(accountId);
        } catch (final Exception e) {
            throw new StatementFactoryException("cannot extract it");
        }
    }
}