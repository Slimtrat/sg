package com.kata;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kata.entity.AccountStatement;
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

    @Test
    public final void accountStatementUnknownAccount() {
        final String accIdUnknown = "Unknown";
        Assertions.assertThrows(ControllerException.class, () -> controller.accountStatement(accIdUnknown));
    }

    /**
     * Could also be in the statement factory test
     */
    @Test
    public final void accountStatementCreateToString() {
        try {
            final Pattern patternCreate = Pattern.compile("\\[.*\\] CREATE OK");
            final String guid = controller.createAccount();
            final List<AccountStatement> stats = controller.accountStatement(guid);
            Assertions.assertEquals(1, stats.size());
            Assertions.assertTrue(patternCreate.matcher(stats.get(0).toString()).find());
        } catch (final ControllerException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Could also be in the statement factory test
     */
    @Test
    public final void accountStatementCreateAndDepositToString() {
        try {
            final int moneyToDeposit = 5;
            final Pattern patternCreate = Pattern.compile("\\[.*\\] CREATE OK");
            final Pattern patternDeposit = Pattern.compile("\\[.*\\] DEPOSIT OK of " + 5);

            final String guid = controller.createAccount();
            controller.deposit(guid, moneyToDeposit);
            final List<AccountStatement> stats = controller.accountStatement(guid);
            Assertions.assertEquals(2, stats.size());
            Assertions.assertTrue(patternCreate.matcher(stats.get(0).toString()).find());
            Assertions.assertTrue(patternDeposit.matcher(stats.get(1).toString()).find());
        } catch (final ControllerException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Hard deposit could make you more poor caused by concurrency on entity
     */
    @Test
    public final void threadSafeDeposit() {
        try {
            final int moneyToDeposit = 5;
            final int nbOfTimeToDeposit = 10000;
            final String guid = controller.createAccount();

            final ExecutorService executor = Executors.newFixedThreadPool(100); // 100 thread parallele
            final LongAdder adder = new LongAdder();
            for (int i = 0; i < nbOfTimeToDeposit; i++) {
                executor.execute(() -> {
                    try {
                        controller.deposit(guid, moneyToDeposit);
                    } catch (final ControllerException e) {
                        Assertions.fail(e);
                    } finally {
                        adder.increment();
                    }
                });
            }
            while (adder.sum() != nbOfTimeToDeposit) {
                System.out.println("waiting ..");
                Thread.sleep(1);
            }
            Assertions.assertEquals(nbOfTimeToDeposit * moneyToDeposit, controller.getBalance(guid));
        } catch (final ControllerException | InterruptedException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Hard withdraw could make you more rich caused by concurrency on entity
     */
    @Test
    public final void threadSafeWithdraw() {
        try {
            final int moneyToWidthDraw = 5;
            final int nbOfTimeToDeposit = 10000;
            final String guid = controller.createAccount();
            controller.deposit(guid, nbOfTimeToDeposit * moneyToWidthDraw);

            final ExecutorService executor = Executors.newFixedThreadPool(100); // 100 thread parallele
            final LongAdder adder = new LongAdder();
            for (int i = 0; i < nbOfTimeToDeposit; i++) {
                executor.execute(() -> {
                    try {
                        controller.withdraw(guid, moneyToWidthDraw);
                    } catch (final ControllerException e) {
                        Assertions.fail(e);
                    } finally {
                        adder.increment();
                    }
                });
            }
            while (adder.sum() != nbOfTimeToDeposit) {
                System.out.println("waiting ..");
                Thread.sleep(1);
            }
            Assertions.assertEquals(0, controller.getBalance(guid));
        } catch (final ControllerException | InterruptedException e) {
            Assertions.fail(e);
        }
    }
}
