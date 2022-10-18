package com.kata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kata.entity.Account;

public final class ControllerTest {
    @Test
    public final void depositTest() {
        final int amountDeposit = 10;
        final Account account = new Account();
        final Controller controller;
        Assertions.assertDoesNotThrow(() -> controller.deposit(account, amountDeposit));
        Assertions.assertEquals(amountDeposit, controller.getBalance(account));
        Assertions.assertEquals(1, controller.accountStatement(account).size());
    }

    @Test
    public final void withDrawNormalTest() {
        final int amountDeposit = 10;
        final int amountWithDraw = 5;
        final Account account = new Account();
        final Controller controller;
        Assertions.assertDoesNotThrow(() -> controller.deposit(account, amountDeposit));
        Assertions.assertDoesNotThrow(() -> controller.withdraw(account, amountWithDraw));
        Assertions.assertEquals(amountDeposit - amountWithDraw, controller.getBalance(account));
        Assertions.assertEquals(2, controller.accountStatement(account).size());
    }

    /*
     * It could be cool but bank won't
     */
    @Test
    public final void withDrawExcessiveTest() {
        final int amountDeposit = 10;
        final int amountWithDraw = 50;
        final Account account = new Account();
        final Controller controller;
        Assertions.assertDoesNotThrow(() -> controller.deposit(account, amountDeposit));
        Assertions.assertThrows(MyException.class, () -> controller.withdraw(account, amountWithDraw));
        Assertions.assertEquals(amountDeposit, controller.getBalance(account));
        Assertions.assertEquals(2, controller.accountStatement(account).size());
    }
}
