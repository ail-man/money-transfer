package com.ail.revolut.app;

import com.ail.revolut.app.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateTest {
	private static Logger logger = LoggerFactory.getLogger(HibernateTest.class);

	@Test
	public void main() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("h2unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		User found = entityManager.find(User.class, 1L);
		logger.info("found=" + found);
	}
}
