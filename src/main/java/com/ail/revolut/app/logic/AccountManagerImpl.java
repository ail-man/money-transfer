package com.ail.revolut.app.logic;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import com.ail.revolut.app.model.Account;
import com.ail.revolut.app.db.HibernateContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class AccountManagerImpl implements AccountManager {
	private static final Logger logger = LoggerFactory.getLogger(AccountManagerImpl.class);

	@Override
	public Long createAccount() {
		Long accountId = null;

		EntityManager em = HibernateContextHolder.createEntityManager();

		EntityTransaction tx = null;
		try {
			logger.info("Account creation started");
			tx = em.getTransaction();
			tx.begin();

			Account account = new Account();
			account.setBalance(0L);

			em.persist(account);

			tx.commit();

			accountId = account.getId();
			logger.info("Account created with id={}", accountId);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.trace(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}

		return accountId;
	}

	@Override
	public Long getBalance(Long id) {
		Long balance = null;

		EntityManager em = HibernateContextHolder.createEntityManager();

		logger.info("Find account with id={}", id);

		Account account = em.find(Account.class, id);
		if (account != null) {
			balance = account.getBalance();
			logger.info("Account found, balance={}", balance);
		} else {
			logger.info("Account not found!");
		}

		return balance;
	}

	@Override
	public void deposit(Long id, Long amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}

		EntityManager em = HibernateContextHolder.createEntityManager();

		EntityTransaction tx = null;
		try {
			logger.info("Deposit to accountId={}, amount={} started", id, amount);
			tx = em.getTransaction();
			tx.begin();

			Account account = em.find(Account.class, id);
			Long balance = account.getBalance();
			balance = balance + amount;
			if (balance < 0) {
				throw new RuntimeException("Overflow");
			}
			account.setBalance(balance);

			em.merge(account);

			tx.commit();
			logger.info("Deposit to accountId={} completed", id);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.trace(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void withdraw(Long id, Long amount) throws NotEnoughFundsException {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}

		EntityManager em = HibernateContextHolder.createEntityManager();

		EntityTransaction tx = null;
		try {
			logger.info("Withdraw from accountId={}, amount={} started", id, amount);
			tx = em.getTransaction();
			tx.begin();

			Account account = em.find(Account.class, id);
			Long balance = account.getBalance();
			if (amount > balance) {
				throw new NotEnoughFundsException("Not enough funds");
			}
			balance = balance - amount;
			account.setBalance(balance);

			em.merge(account);

			tx.commit();
			logger.info("Withdraw from accountId={} completed", id);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.trace(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}
}
