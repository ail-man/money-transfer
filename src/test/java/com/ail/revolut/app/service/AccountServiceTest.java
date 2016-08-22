package com.ail.revolut.app.service;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

public class AccountServiceTest {

	private AccountService accountService = new AccountServiceImpl();
	private Long accountId;

	@Test
	public void testCreateAccount() {
		accountId = accountService.createAccount();
		assertThat(accountId, notNullValue());
	}

	@Test
	public void testGetBallance() {
		accountId = accountService.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountService.getBalance(accountId), is(notNullValue()));
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() {
		accountId = accountService.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountService.getBalance(accountId), equalTo(0L));
	}

	@Test
	public void testDesposit() {
		accountId = accountService.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountService.getBalance(accountId), equalTo(0L));

		accountService.deposit(accountId, 100L);
		assertThat(accountService.getBalance(accountId), equalTo(100L));

		accountService.deposit(accountId, 23L);
		assertThat(accountService.getBalance(accountId), equalTo(123L));
	}

	@Test
	public void testWithdraw() throws Exception {
		accountId = accountService.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountService.getBalance(accountId), equalTo(0L));

		accountService.deposit(accountId, 1000L);
		assertThat(accountService.getBalance(accountId), equalTo(1000L));

		accountService.withdraw(accountId, 10L);
		assertThat(accountService.getBalance(accountId), equalTo(990L));

		accountService.withdraw(accountId, 123L);
		assertThat(accountService.getBalance(accountId), equalTo(867L));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		accountId = accountService.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountService.getBalance(accountId), equalTo(0L));
		assertWithdrawFails(accountId, 5L);

		accountService.deposit(accountId, 30L);
		assertThat(accountService.getBalance(accountId), equalTo(30L));
		assertWithdrawFails(accountId, 100L);
		assertThat(accountService.getBalance(accountId), equalTo(30L));

		accountService.withdraw(accountId, 10L);
		assertThat(accountService.getBalance(accountId), equalTo(20L));
		assertWithdrawFails(accountId, 30L);
		assertThat(accountService.getBalance(accountId), equalTo(20L));

		accountService.withdraw(accountId, 20L);
		assertThat(accountService.getBalance(accountId), equalTo(0L));
		assertWithdrawFails(accountId, 1L);
		assertThat(accountService.getBalance(accountId), equalTo(0L));
	}

	@Test
	public void testAccountDepositOverflow() {
		accountId = accountService.createAccount();
		assertThat(accountId, notNullValue());

		assertThat(accountService.getBalance(accountId), equalTo(0L));

		accountService.deposit(accountId, Long.MAX_VALUE);
		assertThat(accountService.getBalance(accountId), equalTo(Long.MAX_VALUE));

		try {
			accountService.deposit(accountId, 1L);
			fail("Should not transfer");
		} catch (RuntimeException e) {
			assertThat(e.getMessage(), is(notNullValue()));
		}
		assertThat(accountService.getBalance(accountId), equalTo(Long.MAX_VALUE));
	}

	@Test
	public void testAmountMustBePositiveOnly() {
		accountId = accountService.createAccount();
		assertThat(accountId, notNullValue());
		assertThat(accountService.getBalance(accountId), equalTo(0L));

		assertDepositFails(accountId, 0L);
		assertWithdrawFails(accountId, 0L);

		assertDepositFails(accountId, -100L);
		assertWithdrawFails(accountId, -20L);
	}

	private void assertDepositFails(Long id, Long amount) {
		try {
			accountService.deposit(id, amount);
			fail("Should not deposit");
		} catch (Exception e) {
			assertThat(e, instanceOf(IllegalArgumentException.class));
		}
	}

	private void assertWithdrawFails(Long id, Long amount) {
		try {
			accountService.withdraw(id, amount);
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
