package com.ail.revolut.app.logic;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import com.ail.revolut.app.logic.common.AbstractManager;
import com.ail.revolut.app.model.Account;

public class AccountManagerImpl extends AbstractManager implements AccountManager {

	@Override
	public Long createAccount() throws Exception {
		logger.info("Account creation started");
		Long accountId = perform(em -> {
			Account account = new Account();
			account.setBalance(new BigDecimal("0"));
			em.persist(account);
			return account;
		}).getId();
		logger.info("Account created with id={}", accountId);
		return accountId;
	}

	@Override
	public BigDecimal getBalance(Long id) throws Exception {
		Account account = perform(em -> em.find(Account.class, id));
		BigDecimal balance = null;
		if (account != null) {
			balance = account.getBalance();
			logger.info("Account found, balance={}", balance);
		} else {
			logger.info("Account not found!");
		}
		return balance;
	}

	@Override
	public void deposit(Long id, BigDecimal amount) throws Exception {
		if (amount.signum() <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		logger.info("Deposit to accountId={}, amount={} started", id, amount);
		perform(em -> {
			Account account = em.find(Account.class, id);
			BigDecimal balance = account.getBalance();
			balance = balance.add(amount);
			account.setBalance(balance);
			em.merge(account);
			return null;
		});
		logger.info("Deposit to accountId={} completed", id);
	}

	@Override
	public void withdraw(Long id, BigDecimal amount) throws Exception {
		if (amount.signum() <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		logger.info("Withdraw from accountId={}, amount={} started", id, amount);
		perform(em -> {
			Account account = em.find(Account.class, id);
			BigDecimal balance = account.getBalance();
			if (balance.compareTo(amount) < 0) {
				throw new NotEnoughFundsException("Not enough funds");
			}
			balance = balance.subtract(amount);
			account.setBalance(balance);
			return null;
		});
		logger.info("Withdraw from accountId={} completed", id);
	}
}
