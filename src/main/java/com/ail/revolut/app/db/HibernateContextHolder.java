package com.ail.revolut.app.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateContextHolder {
	private static final String PERSISTENCE_UNIT_NAME = "h2unit";

	private static volatile HibernateContextHolder instance;

	private EntityManagerFactory entityManagerFactory;

	private HibernateContextHolder() {
	}

	public static HibernateContextHolder getInstance() {
		if (instance == null) {
			synchronized (HibernateContextHolder.class) {
				if (instance == null) {
					instance = initialize();
				}
			}
		}
		return instance;
	}

	private static HibernateContextHolder initialize() {
		HibernateContextHolder holder = new HibernateContextHolder();
		holder.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		return holder;
	}

	public EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
