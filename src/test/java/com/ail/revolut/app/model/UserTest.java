package com.ail.revolut.app.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class UserTest {

	@Test
	public void testToString() throws Exception {
		User user = new User();

		assertThat(user.toString(), equalTo("User(id=null, name=null, accounts=null)"));

		user.setId(123L);
		user.setName("SomeName");

		assertThat(user.toString(), equalTo("User(id=123, name=SomeName, accounts=null)"));

		Account account1 = new Account();
		account1.setId(555L);
		account1.setBalance(new BigDecimal("10000"));
		account1.setOwner(user);

		Account account2 = new Account();
		account2.setId(95L);
		account2.setBalance(new BigDecimal("4053"));
		account2.setOwner(user);

		user.setAccounts(Arrays.asList(account1, account2));
		assertThat(user.toString(), equalTo("User(id=123, name=SomeName, accounts=[Account(id=555, balance=10000, owner=123), Account(id=95, balance=4053, owner=123)])"));

		user.setAccounts(Collections.emptyList());
		assertThat(user.toString(), equalTo("User(id=123, name=SomeName, accounts=[])"));
	}
}
