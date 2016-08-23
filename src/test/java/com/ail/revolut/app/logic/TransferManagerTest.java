package com.ail.revolut.app.logic;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

public class TransferManagerTest {

	private TransferManager transferManager = new TransferManagerImpl();
	private AccountManager accountManager = new AccountManagerImpl();

	private Long fromId;
	private Long toId;

	@Before
	public void init() throws Exception {
		fromId = accountManager.createAccount();
		toId = accountManager.createAccount();
		assertAccountBalanceEqualsTo(fromId, 0L);
		assertAccountBalanceEqualsTo(toId, 0L);
	}

	@Test
	public void testTransfer() throws Exception {
		depositTo(fromId, 100L);

		assertAccountBalanceEqualsTo(fromId, 100L);
		assertAccountBalanceEqualsTo(toId, 0L);

		transferManager.transfer(fromId, toId, 10L);
		assertAccountBalanceEqualsTo(fromId, 90L);
		assertAccountBalanceEqualsTo(toId, 10L);

		transferManager.transfer(fromId, toId, 20L);
		assertAccountBalanceEqualsTo(fromId, 70L);
		assertAccountBalanceEqualsTo(toId, 30L);

		transferManager.transfer(fromId, toId, 70L);
		assertAccountBalanceEqualsTo(fromId, 0L);
		assertAccountBalanceEqualsTo(toId, 100L);
	}

	@Test
	public void testTransferAmountMustBeNotGreaterThanFromBalance() throws Exception {
		assertTransferFails(fromId, toId, 10L);
		assertAccountBalanceEqualsTo(fromId, 0L);
		assertAccountBalanceEqualsTo(toId, 0L);

		depositTo(fromId, 100L);
		assertAccountBalanceEqualsTo(fromId, 100L);

		assertTransferFails(fromId, toId, 1000L);
		assertAccountBalanceEqualsTo(fromId, 100L);
		assertAccountBalanceEqualsTo(toId, 0L);
	}

	@Test
	public void testTransferToBalanceOverflow() throws Exception {
		depositTo(fromId, 1L);
		depositTo(toId, Long.MAX_VALUE);
		assertAccountBalanceEqualsTo(fromId, 1L);
		assertAccountBalanceEqualsTo(toId, Long.MAX_VALUE);

		try {
			transferManager.transfer(fromId, toId, 1L);
			fail("Should not transfer");
		} catch (RuntimeException e) {
			assertThat(e.getMessage(), is(notNullValue()));
		}

		assertAccountBalanceEqualsTo(fromId, 1L);
		assertAccountBalanceEqualsTo(toId, Long.MAX_VALUE);
	}

	@Test
	public void testTransferShouldIncrementToBalance() throws Exception {
		depositTo(fromId, 100L);
		depositTo(toId, 200L);
		assertAccountBalanceEqualsTo(fromId, 100L);
		assertAccountBalanceEqualsTo(toId, 200L);

		Long amount = 20L;
		Long toBalance = getBalance(toId);
		transferManager.transfer(fromId, toId, amount);
		assertAccountBalanceEqualsTo(toId, toBalance + amount);
	}

	@Test
	public void testTransferShouldDecrementFromBalance() throws Exception {
		depositTo(fromId, 100L);
		assertAccountBalanceEqualsTo(fromId, 100L);

		Long amount = 20L;
		Long fromBalance = getBalance(fromId);

		transferManager.transfer(fromId, toId, amount);
		assertAccountBalanceEqualsTo(fromId, fromBalance - amount);
	}

	@Test
	public void testAmountShouldBeOnlyPositive() throws Exception {
		assertTransferFails(fromId, toId, 0L);
		assertTransferFails(fromId, toId, -10L);
	}

	private Long getBalance(Long accountId) {
		return accountManager.getBalance(accountId);
	}

	private void assertAccountBalanceEqualsTo(Long accountId, Long balance) {
		assertThat(getBalance(accountId), equalTo(balance));
	}

	private void depositTo(Long accountId, Long amount) {
		accountManager.deposit(accountId, amount);
	}

	private void assertTransferFails(Long fromId, Long toId, Long amount) {
		try {
			transferManager.transfer(fromId, toId, amount);
			fail("Should not transfer");
		} catch (Exception e) {
			if (amount > 0) {
				assertThat(e, instanceOf(NotEnoughFundsException.class));
			} else {
				assertThat(e, instanceOf(IllegalArgumentException.class));
			}
		}
	}
}
