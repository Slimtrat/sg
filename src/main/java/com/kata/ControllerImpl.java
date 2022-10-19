package com.kata;

import java.util.List;

import com.kata.exception.AccountFactoryException;
import com.kata.exception.ControllerException;
import com.kata.exception.MyException;
import com.kata.exception.StatementFactoryException;
import com.kata.factory.AccountFactory;
import com.kata.factory.StatementFactory;
import com.kata.models.Account;
import com.kata.models.AccountStatement;

public final class ControllerImpl implements Controller {
    private final StatementFactory factory;
    private final AccountFactory accountFactory;
    private final ConcurrentModelLocker<String> locks;

    public ControllerImpl(final StatementFactory factory, final AccountFactory accountFactory) {
        this.factory = factory;
        this.accountFactory = accountFactory;
        this.locks = new ConcurrentModelLocker<>();
    }

    @Override
    public final void deposit(final String accId, final float amount) throws ControllerException {
        final AccountStatement statement = new AccountStatement(ActionEnum.DEPOSIT, amount);
        try {
            locks.lockEntity(accId);
            final Account account = checkAccount(accId);
            account.deposit(amount);
            accountFactory.update(account);
        } catch (final AccountFactoryException e) {
            statement.hasFail();
            throw new ControllerException(e.getMessage());
        } finally {
            locks.unlockEntity(accId);
            factory.save(accId, statement);
        }
    }

    @Override
    public final void withdraw(final String accFrom, final float amount) throws ControllerException {
        final AccountStatement statement = new AccountStatement(ActionEnum.WITHDRAW, amount);
        try {
            locks.lockEntity(accFrom);
            final Account account = checkAccount(accFrom);
            account.withdraw(amount);
            accountFactory.update(account);
        } catch (final AccountFactoryException | MyException e) {
            statement.hasFail();
            throw new ControllerException(e.getMessage());
        } finally {
            locks.unlockEntity(accFrom);
            factory.save(accFrom, statement);
        }
    }

    @Override
    public final float getBalance(final String accFrom) throws ControllerException {
        final AccountStatement statement = new AccountStatement(ActionEnum.SHOW);
        try {
            final Account account = checkAccount(accFrom);
            return account.getBalance();
        } catch (final AccountFactoryException e) {
            statement.hasFail();
            throw new ControllerException(e.getMessage());
        } finally {
            factory.save(accFrom, statement);
        }
    }

    @Override
    public final List<AccountStatement> accountStatement(final String accFrom) throws ControllerException {
        try {
            return factory.show(accFrom);
        } catch (final StatementFactoryException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @Override
    public final String createAccount() throws ControllerException {
        try {
            final AccountStatement statement = new AccountStatement(ActionEnum.CREATE);
            final String accId = accountFactory.createAccount();
            factory.save(accId, statement);
            return accId;
        } catch (final AccountFactoryException | StatementFactoryException e) {
            throw new ControllerException("Cannot create the account");
        }
    }

    private final Account checkAccount(final String accId) throws AccountFactoryException {
        return accountFactory.findAccount(accId)//
                .orElseThrow(() -> new AccountFactoryException("Cannot find this account"));
    }
}
