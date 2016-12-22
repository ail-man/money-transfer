package com.ail.revolut.app.db;

import javax.persistence.EntityManager;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Test;

public class HibernateContextHolderTest {

	@Test
	public void testCreateEntityManager() throws Exception {
		EntityManager em1 = HibernateContextHolder.getInstance().createEntityManager();
		assertThat(em1, is(notNullValue()));

		EntityManager em2 = HibernateContextHolder.getInstance().createEntityManager();
		assertThat(em2, is(notNullValue()));

		assertTrue(em1 != em2);
	}

	@Test
	public void testSingleHibernateContext() throws Exception {
		HibernateContextHolder contextHolder1 = HibernateContextHolder.getInstance();
		HibernateContextHolder contextHolder2 = HibernateContextHolder.getInstance();

		assertTrue(contextHolder1 == contextHolder2);
	}

}
