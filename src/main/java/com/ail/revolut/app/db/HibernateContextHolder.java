package com.ail.revolut.app.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateContextHolder {
	private static final String PERSISTENCE_UNIT_NAME = "h2unit";

	private static EntityManagerFactory entityManagerFactory;

	private HibernateContextHolder() {
	}

	public static EntityManager createEntityManager() {
		return createEntityManagerFactory().createEntityManager();
	}

	static EntityManagerFactory createEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return entityManagerFactory;
	}

}