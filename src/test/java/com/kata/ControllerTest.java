package com.kata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kata.exception.ControllerException;
import com.kata.factory.AccountFactory;
import com.kata.factory.AccountFactoryImpl;
import com.kata.factory.StatementFactory;
import com.kata.factory.StatementFactoryImpl;

public final class ControllerTest {
    private StatementFactory statFactory;
    private AccountFactory accFactory;
    private Controller controller;

    @BeforeEach
    public final void initFactory() {
        statFactory = new StatementFactoryImpl();
        accFactory = new AccountFactoryImpl();
        controller = new ControllerImpl(statFactory, accFactory);
    }

    @Test
    public final void depositTest() {
        final int amountDeposit = 10;
        try {
            final String accId = controller.createAccount();
            Assertions.assertDoesNotThrow(() -> controller.deposit(accId, amountDeposit));
            Assertions.assertEquals(amountDeposit, controller.getBalance(accId));
            Assertions.assertEquals(3, controller.accountStatement(accId).size()); // Create, deposit and show
        } catch (final ControllerException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public final void withDrawNormalTest() {
        final int amountDeposit = 10;
        final int amountWithDraw = 5;
        try {
            final String accId = controller.createAccount();
            Assertions.assertDoesNotThrow(() -> controller.deposit(accId, amountDeposit));
            Assertions.assertDoesNotThrow(() -> controller.withdraw(accId, amountWithDraw));
            Assertions.assertEquals(amountDeposit - amountWithDraw, controller.getBalance(accId));
            Assertions.assertEquals(4, controller.accountStatement(accId).size()); // CREATE DEPOSIT WITHDRAW and SHOW
        } catch (final ControllerException e) {
            Assertions.fail(e);
        }
    }

    /*
     * It could be cool but bank won't
     */
    @Test
    public final void withDrawExcessiveTest() {
        final int amountDeposit = 10;
        final int amountWithDraw = 50;
        try {
            final String accId = controller.createAccount();
            Assertions.assertDoesNotThrow(() -> controller.deposit(accId, amountDeposit));
            Assertions.assertThrows(ControllerException.class, () -> controller.withdraw(accId, amountWithDraw));
            Assertions.assertEquals(amountDeposit, controller.getBalance(accId));
            Assertions.assertEquals(4, controller.accountStatement(accId).size());
        } catch (final ControllerException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public final void withDrawWithUnknownAccount() {
        final String accIdUnknown = "Unknown";
        final int amountWithDraw = 50;
        Assertions.assertThrows(ControllerException.class, () -> controller.withdraw(accIdUnknown, amountWithDraw));
    }

    @Test
    public final void getBalanceWithUnknownAccount() {
        final String accIdUnknown = "Unknown";
        Assertions.assertThrows(ControllerException.class, () -> controller.getBalance(accIdUnknown));
    }

    @Test
    public final void depositWithUnknownAccount() {
        final String accIdUnknown = "Unknown";
        final int amountWithDraw = 50;
        Assertions.assertThrows(ControllerException.class, () -> controller.deposit(accIdUnknown, amountWithDraw));
    }
}
