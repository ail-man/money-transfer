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
	public Account createAccount() {
		Account result = null;

		EntityManager em = HibernateUtil.createEntityManager();

		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			result = new Account();
			result.setBalance(0L);

			em.persist(result);

			tx.commit();

			logger.info("Account created with id = " + result.getId());
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}

		return result;
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
			balance = balance - amount;
			if (balance < 0) {
				throw new NotEnoughFundsException();
			}
			account.setBalance(balance);

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
