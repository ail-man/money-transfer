package com.ail.revolut.app.service;

import com.ail.revolut.app.logic.IAccount;
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
	public void testCreateAccount() {
		IAccount newAccount = accountService.createAccount();

		assertThat(newAccount, notNullValue());
		assertThat(newAccount.getId(), notNullValue());
		assertThat(newAccount.getBalance(), equalTo(0L));
	}

	@Test
	public void testFindAccount() {
		IAccount account = accountService.createAccount();
		IAccount found = accountService.findAccount(account.getId());

		assertThat(found, is(notNullValue()));
		assertThat(found.getId(), is(account.getId()));
		assertThat(found.getBalance(), is(account.getBalance()));
		assertThat(found.getOwner().getId(), is(account.getOwner().getId()));
		assertThat(found.getOwner().getName(), is(account.getOwner().getName()));
	}

}
