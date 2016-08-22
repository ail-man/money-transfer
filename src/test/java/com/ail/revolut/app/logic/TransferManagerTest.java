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
	public void init() {
		fromId = accountManager.createAccount();
		toId = accountManager.createAccount();
	}

	@Test
	public void testTransfer() throws Exception {
		accountManager.deposit(fromId, 100L);

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
	public void testTransferAmountMustBeNotGreaterThanFromBallance() {
		assertAccountBalanceEqualsTo(fromId, 0L);
		assertAccountBalanceEqualsTo(toId, 0L);
		assertTransferFails(fromId, toId, 10L);
		assertAccountBalanceEqualsTo(fromId, 0L);
		assertAccountBalanceEqualsTo(toId, 0L);

		accountManager.deposit(fromId, 100L);
		assertAccountBalanceEqualsTo(fromId, 100L);
		assertAccountBalanceEqualsTo(toId, 0L);
		assertTransferFails(fromId, toId, 1000L);
		assertAccountBalanceEqualsTo(fromId, 100L);
		assertAccountBalanceEqualsTo(toId, 0L);
	}

	@Test
	public void testTransferToBalanceOverflow() throws Exception {
		assertAccountBalanceEqualsTo(fromId, 0L);
		assertAccountBalanceEqualsTo(toId, 0L);

		accountManager.deposit(fromId, 1L);
		accountManager.deposit(toId, Long.MAX_VALUE);
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
		accountManager.deposit(fromId, 100L);
		accountManager.deposit(toId, 200L);
		assertAccountBalanceEqualsTo(fromId, 100L);
		assertAccountBalanceEqualsTo(toId, 200L);

		Long amount = 20L;
		Long toBalance = accountManager.getBalance(toId);
		transferManager.transfer(fromId, toId, amount);
		assertAccountBalanceEqualsTo(toId, toBalance + amount);
	}

	@Test
	public void testTransferShouldDecrementFromBalance() throws Exception {
		assertAccountBalanceEqualsTo(fromId, 0L);
		accountManager.deposit(fromId, 100L);
		assertAccountBalanceEqualsTo(fromId, 100L);

		Long amount = 20L;

		transferManager.transfer(fromId, toId, amount);
		assertAccountBalanceEqualsTo(fromId, 80L);
	}

	@Test
	public void testAmountShouldBeOnlyPositive() {
		assertTransferFails(1L, 2L, 0L);
		assertTransferFails(1L, 2L, -10L);
	}

	private void assertAccountBalanceEqualsTo(Long accountId, Long balance) {
		assertThat(accountManager.getBalance(accountId), equalTo(balance));
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
