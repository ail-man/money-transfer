package com.ail.revolut.app.logic;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

public class AccountManagerTest {

	private AccountManager accountManager = new AccountManagerImpl();
	private Long accountId;

	@Test
	public void testCreateAccount() throws Exception {
		accountId = accountManager.createAccount();
		assertThat(accountId, notNullValue());
	}

	@Test
	public void testGetBallance() throws Exception {
		accountId = accountManager.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountManager.getBalance(accountId), is(notNullValue()));
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		accountId = accountManager.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountManager.getBalance(accountId), equalTo(0L));
	}

	@Test
	public void testDesposit() throws Exception {
		accountId = accountManager.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountManager.getBalance(accountId), equalTo(0L));

		accountManager.deposit(accountId, 100L);
		assertThat(accountManager.getBalance(accountId), equalTo(100L));

		accountManager.deposit(accountId, 23L);
		assertThat(accountManager.getBalance(accountId), equalTo(123L));
	}

	@Test
	public void testWithdraw() throws Exception {
		accountId = accountManager.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountManager.getBalance(accountId), equalTo(0L));

		accountManager.deposit(accountId, 1000L);
		assertThat(accountManager.getBalance(accountId), equalTo(1000L));

		accountManager.withdraw(accountId, 10L);
		assertThat(accountManager.getBalance(accountId), equalTo(990L));

		accountManager.withdraw(accountId, 123L);
		assertThat(accountManager.getBalance(accountId), equalTo(867L));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		accountId = accountManager.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountManager.getBalance(accountId), equalTo(0L));
		assertWithdrawFails(accountId, 5L);

		accountManager.deposit(accountId, 30L);
		assertThat(accountManager.getBalance(accountId), equalTo(30L));
		assertWithdrawFails(accountId, 100L);
		assertThat(accountManager.getBalance(accountId), equalTo(30L));

		accountManager.withdraw(accountId, 10L);
		assertThat(accountManager.getBalance(accountId), equalTo(20L));
		assertWithdrawFails(accountId, 30L);
		assertThat(accountManager.getBalance(accountId), equalTo(20L));

		accountManager.withdraw(accountId, 20L);
		assertThat(accountManager.getBalance(accountId), equalTo(0L));
		assertWithdrawFails(accountId, 1L);
		assertThat(accountManager.getBalance(accountId), equalTo(0L));
	}

	@Test
	public void testAccountDepositOverflow() throws Exception {
		accountId = accountManager.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountManager.getBalance(accountId), equalTo(0L));

		accountManager.deposit(accountId, Long.MAX_VALUE);
		assertThat(accountManager.getBalance(accountId), equalTo(Long.MAX_VALUE));

		try {
			accountManager.deposit(accountId, 1L);
			fail("Should not transfer");
		} catch (RuntimeException e) {
			assertThat(e.getMessage(), is(notNullValue()));
		}
		assertThat(accountManager.getBalance(accountId), equalTo(Long.MAX_VALUE));
	}

	@Test
	public void testAmountMustBePositiveOnly() throws Exception {
		accountId = accountManager.createAccount();
		assertThat(accountId, notNullValue());
		assertThat(accountManager.getBalance(accountId), equalTo(0L));

		assertDepositFails(accountId, 0L);
		assertWithdrawFails(accountId, 0L);

		assertDepositFails(accountId, -100L);
		assertWithdrawFails(accountId, -20L);
	}

	private void assertDepositFails(Long id, Long amount) {
		try {
			accountManager.deposit(id, amount);
			fail("Should not deposit");
		} catch (Exception e) {
			assertThat(e, instanceOf(IllegalArgumentException.class));
		}
	}

	private void assertWithdrawFails(Long id, Long amount) {
		try {
			accountManager.withdraw(id, amount);
			fail("Should not withdraw");
		} catch (Exception e) {
			if (amount > 0) {
				assertThat(e, instanceOf(NotEnoughFundsException.class));
			} else {
				assertThat(e, instanceOf(IllegalArgumentException.class));
			}
		}
	}

}
