package com.ail.revolut.app.service;

import com.ail.revolut.app.NotEnoughFundsException;
import com.ail.revolut.app.model.Account;
import com.ail.revolut.app.utils.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Override
	public Long createAccount() {
		Long accountId = null;

		EntityManager em = HibernateUtil.createEntityManager();

		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			Account account = new Account();
			account.setBalance(0L);

			em.persist(account);

			tx.commit();

			accountId = account.getId();
			logger.info("Account created with id = " + accountId);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}

		return accountId;
	}

	@Override
	public Long getBalance(Long id) {
		Long balance = null;

		EntityManager em = HibernateUtil.createEntityManager();

		logger.info("Find account with id = " + id);

		Account account = em.find(Account.class, id);
		if (account != null) {
			balance = account.getBalance();
			logger.debug("Balance = " + account);
		} else {
			logger.info("Account not found!");
		}

		return balance;
	}

	@Override
	public void deposit(Long id, Long amount) {
		EntityManager em = HibernateUtil.createEntityManager();

		EntityTransaction tx = null;
		try {
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
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void withdraw(Long id, Long amount) throws NotEnoughFundsException {
		EntityManager em = HibernateUtil.createEntityManager();

		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			Account account = em.find(Account.class, id);
			Long balance = account.getBalance();
			if (amount > balance) {
				throw new NotEnoughFundsException();
			}
			balance = balance - amount;
			account.setBalance(balance);

			em.merge(account);

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}
}
