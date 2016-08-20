package com.ail.revolut.app.service;

import com.ail.revolut.app.NotEnoughFundsException;
import com.ail.revolut.app.api.IAccount;
import com.ail.revolut.app.utils.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransferServiceImpl implements TransferService {
	private static Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

	@Override
	public void transfer(IAccount from, IAccount to, long amount) {
		EntityManager em = HibernateUtil.createEntityManager();

		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			logger.info("Transfer from account={} to account={} with amount={} started", from.getId(), to.getId(), amount);
			tx.begin();

			from.withdraw(amount);
			to.deposit(amount);

			// TODO persist values from and to

			em.flush();
			tx.commit();

			logger.info("Transfer completed");
		} catch (NotEnoughFundsException e) {
			logger.error("Not enought funds for transfer! Available funds = {}", from.getBalance());
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
	}
}
