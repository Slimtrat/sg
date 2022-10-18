package com.kata;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kata.entity.Account;
import com.kata.entity.AccountStatement;

public final class StatementFactoryTest {
    /**
     * Test normal save in factory
     */
    @Test
    void testSave() {
        final StatementFactory factory;

        final Account account = new Account();
        final AccountStatement stat = new AccountStatement(0, 0);
        Assertions.assertDoesNotThrow(() -> factory.save(account, stat));
    }

    /**
     * Test normal save in factory with 2 accounts
     */
    @Test
    void testSave2Accounts() {
        final StatementFactory factory;

        final Account account = new Account();
        final Account account2 = new Account();

        final AccountStatement stat = new AccountStatement(0, 0);

        Assertions.assertDoesNotThrow(() -> factory.save(account, stat));
        Assertions.assertDoesNotThrow(() -> factory.save(account2, stat));
    }

    /**
     * Test showing simple with only one account
     */
    @Test
    void testShow() {
        final StatementFactory factory;

        final Account account = new Account();
        final AccountStatement stat = new AccountStatement(0, 0);
        factory.save(account, stat);
        final AccountStatement stat2 = new AccountStatement(5, 4);
        factory.save(account, stat2);

        final List<AccountStatement> stats = factory.show(account);
        Assertions.assertEquals(2, stats.size());
    }
}
