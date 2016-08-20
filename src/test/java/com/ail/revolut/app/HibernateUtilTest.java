package com.ail.revolut.app;

import com.ail.revolut.app.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class HibernateUtilTest {

	@Test
	public void testCreateEntityManager() {
		EntityManager em = HibernateUtil.createEntityManager();
		assertThat(em, is(notNullValue()));
	}

	@Test
	public void testCreateEntityManagerFactory() {
		EntityManagerFactory emf = HibernateUtil.createEntityManagerFactory();
		assertThat(emf, is(notNullValue()));
	}

	@Test
	public void testCreateSessionFactory() {
		SessionFactory sf = HibernateUtil.createSessionFactory();
		assertThat(sf, is(notNullValue()));
	}

	@Test
	public void testCreateSession() {
		Session sf = HibernateUtil.createSession();
		assertThat(sf, is(notNullValue()));
	}
}
