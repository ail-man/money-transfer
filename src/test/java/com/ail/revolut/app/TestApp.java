package com.ail.revolut.app;

import com.ail.revolut.app.model.Account;
import com.ail.revolut.app.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestApp {
	private static Logger logger = LoggerFactory.getLogger(HibernateTest.class);

	@Test
	public void testTransfer() {
		User user = new User();
		user.setName("Artur");

		Account account = new Account();
		account.setOwner(user);
		account.setBallance(1000L);

	}
}
