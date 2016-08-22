package com.ail.revolut.app.db;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HibernateContextHolderTest {

	@Test
	public void testCreateEntityManager() {
		EntityManager em1 = HibernateContextHolder.createEntityManager();
		assertThat(em1, is(notNullValue()));

		EntityManager em2 = HibernateContextHolder.createEntityManager();
		assertThat(em2, is(notNullValue()));

		assertTrue(em1 != em2);
	}

	@Test
	public void testSingleHibernateContext() {
		EntityManagerFactory emf1 = HibernateContextHolder.createEntityManagerFactory();
		EntityManagerFactory emf2 = HibernateContextHolder.createEntityManagerFactory();

		assertTrue(emf1 == emf2);
	}

}
