package com.ail.revolut.app.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	private static final String PERSISTENCE_UNIT_NAME = "h2unit";

	public static EntityManagerFactory createEntityManagerFactory() {
		return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public static SessionFactory createSessionFactory() {
		return HibernateUtil.createEntityManagerFactory().unwrap(SessionFactory.class);
	}

	public static Session createSession() {
		return createSessionFactory().openSession();
	}

	public static EntityManager createEntityManager() {
		return createEntityManagerFactory().createEntityManager();
	}
}