package com.kata;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kata.exception.StatementFactoryException;
import com.kata.factory.StatementFactory;
import com.kata.factory.StatementFactoryImpl;
import com.kata.models.AccountStatement;

public final class StatementFactoryTest {
    private StatementFactory factory;

    @BeforeEach
    public final void initFactory() {
        factory = new StatementFactoryImpl();
    }

    /**
     * Test normal save in factory
     */
    @Test
    public final void should_save() {
        final AccountStatement stat = new AccountStatement(ActionEnum.DEPOSIT, 5);
        Assertions.assertDoesNotThrow(() -> factory.save("2", stat));
    }

    /**
     * Test normal save in factory with 2 accounts
     */
    @Test
    public final void should_save_for_different_account() {
        final AccountStatement stat = new AccountStatement(ActionEnum.WITHDRAW, 8);
        Assertions.assertDoesNotThrow(() -> factory.save("1", stat));
        Assertions.assertDoesNotThrow(() -> factory.save("3", stat));
        try {
            Assertions.assertEquals(1, factory.show("1").size());
        } catch (final StatementFactoryException e) {
            Assertions.fail(e);
        }

    }

    /**
     * Test showing simple with only one account
     */
    @Test
    public final void should_show_for_known() {
        final String accId = "5";
        final AccountStatement stat2 = new AccountStatement(ActionEnum.CREATE);
        final AccountStatement stat = new AccountStatement(ActionEnum.DEPOSIT, 8);
        try {
            factory.save(accId, stat);
            factory.save(accId, stat2);
            final List<AccountStatement> stats = factory.show(accId);
            Assertions.assertEquals(2, stats.size());
        } catch (final StatementFactoryException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Test showing simple with only one account
     */
    @Test
    public final void should_not_show_for_unknown() {
        final String accId = "5";
        Assertions.assertThrows(StatementFactoryException.class, () -> factory.show(accId));
    }
}
