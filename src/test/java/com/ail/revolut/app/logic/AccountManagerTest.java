package com.ail.revolut.app.logic;

import java.math.BigInteger;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

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
	public void testGetBalance() throws Exception {
		assertThat(getBalance(), is(notNullValue()));
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		assertAccountBalanceEqualsTo(new BigInteger("0"));
	}

	@Test
	public void testDesposit() throws Exception {
		deposit(new BigInteger("100"));
		assertAccountBalanceEqualsTo(new BigInteger("100"));

		deposit(new BigInteger("23"));
		assertAccountBalanceEqualsTo(new BigInteger("123"));
	}

	@Test
	public void testWithdraw() throws Exception {
		deposit(new BigInteger("1000"));
		assertAccountBalanceEqualsTo(new BigInteger("1000"));

		withdraw(new BigInteger("10"));
		assertAccountBalanceEqualsTo(new BigInteger("990"));

		withdraw(new BigInteger("123"));
		assertAccountBalanceEqualsTo(new BigInteger("867"));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		assertWithdrawFails(accountId, new BigInteger("5"));

		deposit(new BigInteger("30"));
		assertAccountBalanceEqualsTo(new BigInteger("30"));
		assertWithdrawFails(accountId, new BigInteger("100"));
		assertAccountBalanceEqualsTo(new BigInteger("30"));

		withdraw(new BigInteger("10"));
		assertAccountBalanceEqualsTo(new BigInteger("20"));
		assertWithdrawFails(accountId, new BigInteger("30"));
		assertAccountBalanceEqualsTo(new BigInteger("20"));

		withdraw(new BigInteger("20"));
		assertAccountBalanceEqualsTo(new BigInteger("0"));
		assertWithdrawFails(accountId, new BigInteger("1"));
		assertAccountBalanceEqualsTo(new BigInteger("0"));
	}

	@Test
	public void testAmountMustBePositiveOnly() throws Exception {
		assertDepositFails(accountId, new BigInteger("0"));
		assertWithdrawFails(accountId, new BigInteger("0"));

		assertDepositFails(accountId, new BigInteger("-100"));
		assertWithdrawFails(accountId, new BigInteger("-20"));
	}

	private BigInteger getBalance() {
		return accountManager.getBalance(accountId);
	}

	private void deposit(BigInteger amount) {
		accountManager.deposit(accountId, amount);
	}

	private void withdraw(BigInteger amount) throws NotEnoughFundsException {
		accountManager.withdraw(accountId, amount);
	}

	private void assertAccountBalanceEqualsTo(BigInteger balance) {
		assertThat(accountManager.getBalance(accountId), equalTo(balance));
	}

	private void assertDepositFails(Long id, BigInteger amount) {
		try {
			accountManager.deposit(id, amount);
			fail("Should not deposit");
		} catch (Exception e) {
			assertThat(e, instanceOf(IllegalArgumentException.class));
		}
	}

	private void assertWithdrawFails(Long id, BigInteger amount) {
		try {
			accountManager.withdraw(id, amount);
			fail("Should not withdraw");
		} catch (Exception e) {
			if (amount.signum() > 0) {
				assertThat(e, instanceOf(NotEnoughFundsException.class));
			} else {
				assertThat(e, instanceOf(IllegalArgumentException.class));
			}
		}
	}

}
