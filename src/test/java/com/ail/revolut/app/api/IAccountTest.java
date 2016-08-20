package com.ail.revolut.app.api;

import com.ail.revolut.app.NotEnoughFundsException;
import com.ail.revolut.app.service.AccountService;
import com.ail.revolut.app.service.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.fail;

// TODO move all logic from account to account service
public class IAccountTest {
	private static final Logger logger = LoggerFactory.getLogger(IAccountTest.class);

	private IAccount account;

	@Before
	public void init() {
		AccountService accountService = new AccountServiceImpl();
		account = accountService.createAccount();
	}

	@Test
	public void testNewAccountHasZeroBalance() {
		assertThat(account.getBalance(), equalTo(0L));
	}

	@Test
	public void testDesposit() {
		account.deposit(100L);
		assertThat(account.getBalance(), equalTo(100L));

		account.deposit(23L);
		assertThat(account.getBalance(), equalTo(123L));
	}

	@Test
	public void testWithdraw() throws Exception {
		account.deposit(1000L);

		assertThat(account.getBalance(), equalTo(1000L));

		account.withdraw(10L);
		assertThat(account.getBalance(), equalTo(990L));

		account.withdraw(123L);
		assertThat(account.getBalance(), equalTo(867L));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		assertThat(account.getBalance(), equalTo(0L));
		assertWithdrawFails(account, 5L);

		account.deposit(30L);
		assertThat(account.getBalance(), equalTo(30L));
		assertWithdrawFails(account, 100L);
		assertThat(account.getBalance(), equalTo(30L));

		account.withdraw(10L);
		assertThat(account.getBalance(), equalTo(20L));
		assertWithdrawFails(account, 30L);
		assertThat(account.getBalance(), equalTo(20L));

		account.withdraw(20L);
		assertThat(account.getBalance(), equalTo(0L));
		assertWithdrawFails(account, 1L);
		assertThat(account.getBalance(), equalTo(0L));
	}

	private void assertWithdrawFails(IAccount account, long amount) {
		try {
			account.withdraw(amount);
			fail("Should not withdraw");
		} catch (Exception e) {
			assertThat(e, instanceOf(NotEnoughFundsException.class));
		}
	}

	@Test(expected = RuntimeException.class)
	public void testAccountDepositOverflow() {
		account.deposit(Long.MAX_VALUE);

		assertThat(account.getBalance(), equalTo(Long.MAX_VALUE));
		account.deposit(1L);
	}


}
