package com.ail.revolut.app.logic;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

public class AccountManagerTest {

	private AccountManager accountManager = new AccountManagerImpl();
	private Long accountId;

	@Before
	public void init() {
		accountId = accountManager.createAccount();
	}

	@Test
	public void testCreateAccount() throws Exception {
		assertThat(accountId, notNullValue());
	}

	@Test
	public void testGetBallance() throws Exception {
		assertThat(getBalance(), is(notNullValue()));
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		assertAccountBalanceEqualsTo(0L);
	}

	@Test
	public void testDesposit() throws Exception {
		deposit(100L);
		assertAccountBalanceEqualsTo(100L);

		deposit(23L);
		assertAccountBalanceEqualsTo(123L);
	}

	@Test
	public void testWithdraw() throws Exception {
		deposit(1000L);
		assertAccountBalanceEqualsTo(1000L);

		withdraw(10L);
		assertAccountBalanceEqualsTo(990L);

		withdraw(123L);
		assertAccountBalanceEqualsTo(867L);
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		assertWithdrawFails(accountId, 5L);

		deposit(30L);
		assertAccountBalanceEqualsTo(30L);
		assertWithdrawFails(accountId, 100L);
		assertAccountBalanceEqualsTo(30L);

		withdraw(10L);
		assertAccountBalanceEqualsTo(20L);
		assertWithdrawFails(accountId, 30L);
		assertAccountBalanceEqualsTo(20L);

		withdraw(20L);
		assertAccountBalanceEqualsTo(0L);
		assertWithdrawFails(accountId, 1L);
		assertAccountBalanceEqualsTo(0L);
	}

	@Test
	public void testAccountDepositOverflow() throws Exception {
		deposit(Long.MAX_VALUE);
		assertAccountBalanceEqualsTo(Long.MAX_VALUE);

		try {
			deposit(1L);
			fail("Should not transfer");
		} catch (RuntimeException e) {
			assertThat(e.getMessage(), is(notNullValue()));
		}
		assertAccountBalanceEqualsTo(Long.MAX_VALUE);
	}

	@Test
	public void testAmountMustBePositiveOnly() throws Exception {
		assertDepositFails(accountId, 0L);
		assertWithdrawFails(accountId, 0L);

		assertDepositFails(accountId, -100L);
		assertWithdrawFails(accountId, -20L);
	}

	private Long getBalance() {
		return accountManager.getBalance(accountId);
	}

	private void deposit(Long amount) {
		accountManager.deposit(accountId, amount);
	}

	private void withdraw(Long amount) throws NotEnoughFundsException {
		accountManager.withdraw(accountId, amount);
	}

	private void assertAccountBalanceEqualsTo(Long balance) {
		assertThat(accountManager.getBalance(accountId), equalTo(balance));
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
