package com.kata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kata.exception.MyException;
import com.kata.models.Account;

public final class AccountTest {
    /**
     * Test equals
     */
    @Test
    void should_not_equals_when_different_guid() {
        final Account account = new Account();
        final Account account2 = new Account();
        // guid forcement different
        Assertions.assertNotEquals(account, account2);
    }

    /**
     * Test equals
     */
    @Test
    void should_not_equals_when_wrong_type() {
        final Account account = new Account();
        final int wrongType = 0;
        // guid forcement different
        Assertions.assertNotEquals(account, wrongType);
    }

    /**
     * Test deposit
     */
    @Test
    void should_deposit() {
        final float ammountToDeposit = 5;

        final Account account = new Account();
        account.deposit(ammountToDeposit);

        Assertions.assertEquals(ammountToDeposit, account.getBalance());
    }

    /**
     * Test normal withDraw
     */
    @Test
    void should_withdraw() {
        final float ammountToDeposit = 5;
        final float ammountToWithdraw = 2;

        final Account account = new Account();
        account.deposit(ammountToDeposit);

        Assertions.assertEquals(ammountToDeposit, account.getBalance());
        Assertions.assertDoesNotThrow(() -> account.withdraw(ammountToWithdraw));
        Assertions.assertEquals(ammountToDeposit - ammountToWithdraw, account.getBalance());
    }

    /**
     * Test anormal withDraw
     */
    @Test
    void should_not_widthdraw_when_excessive_amount() {
        final float ammountToDeposit = 5;
        final float ammountToWithdraw = 6;

        final Account account = new Account();
        account.deposit(ammountToDeposit);
        Assertions.assertEquals(ammountToDeposit, account.getBalance());

        Assertions.assertThrows(MyException.class, () -> account.withdraw(ammountToWithdraw));
        Assertions.assertEquals(ammountToDeposit, account.getBalance());
    }
}
