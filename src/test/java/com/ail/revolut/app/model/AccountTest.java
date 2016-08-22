package com.ail.revolut.app.model;

import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountTest {

	@Test
	public void testToString() {
		Account account = new Account();

		assertThat(account.toString(), equalTo("Account(id=null, balance=null, owner=null)"));

		account.setId(777L);
		account.setBalance(10500L);

		assertThat(account.toString(), equalTo("Account(id=777, balance=10500, owner=null)"));

		User user = new User();

		assertThat(account.toString(), equalTo("Account(id=777, balance=10500, owner=null)"));

		user.setId(345L);
		user.setName("User Name");
		user.setAccounts(Collections.singletonList(account));

		account.setOwner(user);
		assertThat(account.toString(), equalTo("Account(id=777, balance=10500, owner=345)"));
	}
}
