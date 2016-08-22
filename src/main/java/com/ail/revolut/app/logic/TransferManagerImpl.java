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
			tx = em.getTransaction();
			logger.info("Transfer from accountId={} to accountId={} with amount={} started", fromAccountId, toAccountId, amount);
			tx.begin();

			Account fromAccount = em.find(Account.class, fromAccountId);
			Long fromBallance = fromAccount.getBalance();
			fromAccount.setBalance(fromBallance - amount);
			if (fromBallance < amount) {
				throw new NotEnoughFundsException("Not enough funds!");
			}

			Account toAccount = em.find(Account.class, toAccountId);
			Long toBallance = toAccount.getBalance();
			toBallance = toBallance + amount;
			if (toBallance < 0) {
				throw new RuntimeException("Overflow");
			}
			toAccount.setBalance(toBallance);

			em.merge(fromAccount);
			em.merge(toAccount);

			tx.commit();

			logger.info("Transfer completed");
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.debug(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}
}