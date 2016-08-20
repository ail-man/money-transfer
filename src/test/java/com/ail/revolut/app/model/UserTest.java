package com.ail.revolut.app.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class UserTest {

	private static final Logger logger = LoggerFactory.getLogger(UserTest.class);

	@Test
	public void testToString() {
		User user = new User();
		user.setId(123);
		user.setName("SomeName");

		Account account = new Account();
		account.setId(555L);
		account.setBallance(10000L);

		account.setOwner(user);

		user.setAccounts(Collections.singletonList(account));

		logger.info(account.toString());
		logger.info(user.toString());
	}
}
