package com.ail.revolut.app.utils;

import com.ail.revolut.app.db.HibernateContextHolder;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class HibernateContextHolderTest {

	@Test
	public void testCreateEntityManager() {
		EntityManager em = HibernateContextHolder.createEntityManager();
		assertThat(em, is(notNullValue()));
	}

}
