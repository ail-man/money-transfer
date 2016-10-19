package com.ail.revolut.app.logic;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.ail.revolut.app.db.HibernateContextHolder;
import com.ail.revolut.app.exception.NotEnoughFundsException;
import com.ail.revolut.app.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			account.setBalance(new BigInteger("0"));

			em.persist(account);

			tx.commit();

			accountId = account.getId();
			logger.info("Account created with id={}", accountId);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			logger.trace(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}

		return accountId;
	}

	@Override
	public BigInteger getBalance(Long id) {
		BigInteger balance = null;

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
	public void deposit(Long id, BigInteger amount) {
		if (amount.signum() <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}

		EntityManager em = HibernateContextHolder.createEntityManager();

		EntityTransaction tx = null;
		try {
			logger.info("Deposit to accountId={}, amount={} started", id, amount);
			tx = em.getTransaction();
			tx.begin();

			Account account = em.find(Account.class, id);
			BigInteger balance = account.getBalance();
			balance = balance.add(amount);
			account.setBalance(balance);

			em.merge(account);

			tx.commit();
			logger.info("Deposit to accountId={} completed", id);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			logger.trace(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void withdraw(Long id, BigInteger amount) throws NotEnoughFundsException {
		if (amount.signum() <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}

		EntityManager em = HibernateContextHolder.createEntityManager();

		EntityTransaction tx = null;
		try {
			logger.info("Withdraw from accountId={}, amount={} started", id, amount);
			tx = em.getTransaction();
			tx.begin();

			Account account = em.find(Account.class, id);
			BigInteger balance = account.getBalance();
			if (balance.compareTo(amount) < 0) {
				throw new NotEnoughFundsException("Not enough funds");
			}
			balance = balance.subtract(amount);
			account.setBalance(balance);

			em.merge(account);

			tx.commit();
			logger.info("Withdraw from accountId={} completed", id);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			logger.trace(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}
}
