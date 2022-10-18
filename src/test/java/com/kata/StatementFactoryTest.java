package com.kata;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kata.entity.AccountStatement;
import com.kata.exception.AccountFactoryException;
import com.kata.factory.StatementFactory;
import com.kata.factory.StatementFactoryImpl;

public final class StatementFactoryTest {
    /**
     * Test normal save in factory
     */
    @Test
    void testSave() {
        final StatementFactory factory = new StatementFactoryImpl();
        final AccountStatement stat = new AccountStatement(ActionEnum.DEPOSIT, 5);
        Assertions.assertDoesNotThrow(() -> factory.save(2, stat));
    }

    /**
     * Test normal save in factory with 2 accounts
     */
    @Test
    void testSave2Accounts() {
        final StatementFactory factory = new StatementFactoryImpl();
        final AccountStatement stat = new AccountStatement(ActionEnum.WITHDRAW, 8);

        Assertions.assertDoesNotThrow(() -> factory.save(1, stat));
        Assertions.assertDoesNotThrow(() -> factory.save(3, stat));
        try {
            Assertions.assertEquals(1, factory.show(1).size());
        } catch (final AccountFactoryException e) {
            Assertions.fail(e);
        }

    }

    /**
     * Test showing simple with only one account
     */
    @Test
    void testShow() {
        final int accId = 5;
        final StatementFactory factory = new StatementFactoryImpl();
        final AccountStatement stat2 = new AccountStatement(ActionEnum.CREATE);
        final AccountStatement stat = new AccountStatement(ActionEnum.DEPOSIT, 8);
        factory.save(accId, stat);
        factory.save(accId, stat2);
        try {
            final List<AccountStatement> stats = factory.show(accId);
            Assertions.assertEquals(2, stats.size());
        } catch (final AccountFactoryException e) {
            Assertions.fail(e);
        }
    }
}
