package com.ail.revolut.app.service;

import com.ail.revolut.app.model.Account;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AccountServiceTest {

	private static Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);

	private AccountService accountService = new AccountServiceImpl();

	@Test
	public void testSavingAccount() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2unit");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			Account account1 = new Account();
			Account account2 = new Account();

			logger.info("before save : account id's = " + account1.getId() + " , " + account2.getId());

			em.persist(account1);
			em.persist(account2);

			em.flush();
			tx.commit();

			logger.info("after save : account id's = " + account1.getId() + " , " + account2.getId());
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.error(e.getMessage(), e);
		} finally {
			em.close();
		}
	}

	@Test
	public void testCreateAccount() {
		Account newAccount = accountService.createAccount();

		assertThat(newAccount, notNullValue());
		assertThat(newAccount.getId(), notNullValue());
		assertThat(newAccount.getBalance(), equalTo(0L));
	}

	@Test
	public void testFindAccount() {
		Account account = accountService.createAccount();
		Account found = accountService.findAccount(account.getId());

		assertThat(found, is(notNullValue()));
		assertThat(found.getId(), is(account.getId()));
		assertThat(found.getBalance(), is(account.getBalance()));
		assertThat(found.getOwner().getId(), is(account.getOwner().getId()));
		assertThat(found.getOwner().getName(), is(account.getOwner().getName()));
	}

}
