package com.ail.revolut.app.logic;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class TransferManagerTest {

	private TransferManager transferManager = new TransferManagerImpl();
	private AccountManager accountManager = new AccountManagerImpl();

	private Long fromId;
	private Long toId;

	@Before
	public void init() throws Exception {
		fromId = accountManager.createAccount();
		toId = accountManager.createAccount();
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("0"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));
	}

	@Test
	public void testTransfer() throws Exception {
		depositTo(fromId, new BigDecimal("100"));

		assertAccountBalanceEqualsTo(fromId, new BigDecimal("100"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));

		transferManager.transfer(fromId, toId, new BigDecimal("10"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("90"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("10"));

		transferManager.transfer(fromId, toId, new BigDecimal("20"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("70"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("30"));

		transferManager.transfer(fromId, toId, new BigDecimal("70"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("0"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("100"));
	}

	@Test
	public void testTransferAmountMustBeNotGreaterThanFromBalance() throws Exception {
		assertTransferFails(fromId, toId, new BigDecimal("10"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("0"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));

		depositTo(fromId, new BigDecimal("100"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("100"));

		assertTransferFails(fromId, toId, new BigDecimal("1000"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("100"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));
	}

	@Test
	public void testTransferShouldIncrementToBalance() throws Exception {
		depositTo(fromId, new BigDecimal("100"));
		depositTo(toId, new BigDecimal("200"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("100"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("200"));

		BigDecimal amount = new BigDecimal("20");
		BigDecimal toBalance = getBalance(toId);
		transferManager.transfer(fromId, toId, amount);
		assertAccountBalanceEqualsTo(toId, toBalance.add(amount));
	}

	@Test
	public void testTransferShouldDecrementFromBalance() throws Exception {
		depositTo(fromId, new BigDecimal("100"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("100"));

		BigDecimal amount = new BigDecimal("20");
		BigDecimal fromBalance = getBalance(fromId);

		transferManager.transfer(fromId, toId, amount);
		assertAccountBalanceEqualsTo(fromId, fromBalance.subtract(amount));
	}

	@Test
	public void testAmountShouldBeOnlyPositive() throws Exception {
		assertTransferFails(fromId, toId, new BigDecimal("0"));
		assertTransferFails(fromId, toId, new BigDecimal("-10"));
	}

	private BigDecimal getBalance(Long accountId) {
		return accountManager.getBalance(accountId);
	}

	private void assertAccountBalanceEqualsTo(Long accountId, BigDecimal balance) {
		assertThat(getBalance(accountId), equalTo(balance));
	}

	private void depositTo(Long accountId, BigDecimal amount) {
		accountManager.deposit(accountId, amount);
	}

	private void assertTransferFails(Long fromId, Long toId, BigDecimal amount) {
		try {
			transferManager.transfer(fromId, toId, amount);
			fail("Should not transfer");
		} catch (Exception e) {
			if (amount.signum() > 0) {
				assertThat(e, instanceOf(NotEnoughFundsException.class));
			} else {
				assertThat(e, instanceOf(IllegalArgumentException.class));
			}
		}
	}
}
