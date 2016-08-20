package com.ail.revolut.app.service;

import com.ail.revolut.app.model.Account;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AccountServiceTest {

	@Test
	public void testCreateAccount() {
		AccountService accountService = new AccountServiceImpl();

		Account newAccount = accountService.createAccount();

		assertThat(newAccount, notNullValue());
		assertThat(newAccount.getBallance(), equalTo(0L));
	}
}
