package com.kata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kata.entity.Account;
import com.kata.exception.MyException;

/**
 * User stories
 */
public final class AccountTest {
    /**
     * Test equals
     */
    @Test
    void testEqualsWrongGuid() {
        final Account account = new Account();
        final Account account2 = new Account();
        // guid forcement different
        Assertions.assertNotEquals(account, account2);
    }

    /**
     * Test equals
     */
    @Test
    void testNotEqualsWrongType() {
        final Account account = new Account();
        final int wrongType = 0;
        // guid forcement different
        Assertions.assertNotEquals(account, wrongType);
    }

    /**
     * Test deposit
     */
    @Test
    void testDeposit() {
        final float ammountToDeposit = 5;

        final Account account = new Account();
        account.deposit(ammountToDeposit);

        Assertions.assertEquals(ammountToDeposit, account.getBalance());
    }

    /**
     * Test normal withDraw
     */
    @Test
    void testWithdraw() {
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
    void testWithdrawExcessive() {
        final float ammountToDeposit = 5;
        final float ammountToWithdraw = 6;

        final Account account = new Account();
        account.deposit(ammountToDeposit);
        Assertions.assertEquals(ammountToDeposit, account.getBalance());

        Assertions.assertThrows(MyException.class, () -> account.withdraw(ammountToWithdraw));
        Assertions.assertEquals(ammountToDeposit, account.getBalance());
    }
}
