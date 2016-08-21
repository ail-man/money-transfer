package com.ail.revolut.app.service;

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

			em.persist(result);

			em.flush();
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
	public Account findAccount(Long id) {
		Account result;

		EntityManager em = HibernateUtil.createEntityManager();

		logger.info("Find account with id = " + id);
		result = em.find(Account.class, id);

		if (result == null) {
			logger.info("Account not found!");
		} else {
			logger.info("Account found: " + result);
		}

		return result;
	}
}
