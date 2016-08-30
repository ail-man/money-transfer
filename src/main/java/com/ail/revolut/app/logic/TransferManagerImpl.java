package com.ail.revolut.app.logic;

import com.ail.revolut.app.db.HibernateContextHolder;
import com.ail.revolut.app.exception.NotEnoughFundsException;
import com.ail.revolut.app.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransferManagerImpl implements TransferManager {
	private static Logger logger = LoggerFactory.getLogger(TransferManagerImpl.class);

	@Override
	public void transfer(Long fromAccountId, Long toAccountId, Long amount) throws NotEnoughFundsException {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		EntityManager em = HibernateContextHolder.createEntityManager();

		EntityTransaction tx = null;
		try {
			logger.info("Transfer fromId={} toId={}, amount={} started", fromAccountId, toAccountId, amount);
			tx = em.getTransaction();
			tx.begin();

			Account fromAccount = em.find(Account.class, fromAccountId);
			Long fromBalance = fromAccount.getBalance();
			if (fromBalance < amount) {
				throw new NotEnoughFundsException("Not enough funds");
			}
			fromBalance -= amount;

			Account toAccount = em.find(Account.class, toAccountId);
			Long toBalance = toAccount.getBalance();
			toBalance += amount;
			if (toBalance < 0) {
				throw new RuntimeException("Overflow");
			}

			fromAccount.setBalance(fromBalance);
			toAccount.setBalance(toBalance);

			em.merge(fromAccount);
			em.merge(toAccount);

			tx.commit();

			logger.info("Transfer fromId={} toId={} completed", fromAccountId, toAccountId);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.trace(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}
}
