package com.ail.revolut.app;

import com.ail.revolut.app.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateTest {
	private static Logger logger = LoggerFactory.getLogger(HibernateTest.class);

	@Test
	@Ignore
	public void testPersistenceLayer() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("h2unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		User foundUser = entityManager.find(User.class, 1L);
		logger.info("user found = " + foundUser);

		foundUser.getAccounts().forEach(account -> logger.info(account.toString()));

		foundUser = entityManager.find(User.class, 2L);
		logger.info("user found = " + foundUser);

		foundUser.getAccounts().forEach(account -> logger.info(account.toString()));
	}
}
