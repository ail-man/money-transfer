package com.ail.revolut.app.service;

import com.ail.revolut.app.NotEnoughFundsException;
import com.ail.revolut.app.model.Account;
import com.ail.revolut.app.utils.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransferServiceImpl implements TransferService {
	private static Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

	private AccountService accountService;

	public TransferServiceImpl() {
		accountService = new AccountServiceImpl();
	}

	@Override
	public void transfer(Long fromAccountId, Long toAccountId, Long amount) throws NotEnoughFundsException {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}
		EntityManager em = HibernateUtil.createEntityManager();

		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			logger.info("Transfer from accountId={} to accountId={} with amount={} started", fromAccountId, toAccountId, amount);
			tx.begin();

			Account fromAccount = em.find(Account.class, fromAccountId);
			Long fromBallance = fromAccount.getBalance();
			fromAccount.setBalance(fromBallance - amount);
			if (fromBallance < amount) {
				throw new NotEnoughFundsException();
			}

			Account toAccount = em.find(Account.class, toAccountId);
			Long toBallance = toAccount.getBalance();
			toAccount.setBalance(toBallance + amount);

			em.merge(fromAccount);
			em.merge(toAccount);

			tx.commit();

			logger.info("Transfer completed");
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}
}
