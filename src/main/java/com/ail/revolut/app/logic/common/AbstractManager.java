package com.ail.revolut.app.logic.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.ail.revolut.app.db.HibernateContextHolder;
import com.ail.revolut.app.logic.AccountManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractManager {
	protected static final Logger logger = LoggerFactory.getLogger(AccountManagerImpl.class);

	protected <T> T perform(Operation<T> operation) throws Exception {
		EntityManager em = HibernateContextHolder.createEntityManager();
		EntityTransaction tx = null;
		T result;
		try {
			tx = em.getTransaction();
			tx.begin();
			result = operation.perform(em);
			tx.commit();
		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			logger.trace(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}
		return result;
	}
}
