package com.ail.revolut.app.logic;

import java.math.BigInteger;

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
		assertAccountBalanceEqualsTo(fromId, new BigInteger("0"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("0"));
	}

	@Test
	public void testTransfer() throws Exception {
		depositTo(fromId, new BigInteger("100"));

		assertAccountBalanceEqualsTo(fromId, new BigInteger("100"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("0"));

		transferManager.transfer(fromId, toId, new BigInteger("10"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("90"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("10"));

		transferManager.transfer(fromId, toId, new BigInteger("20"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("70"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("30"));

		transferManager.transfer(fromId, toId, new BigInteger("70"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("0"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("100"));
	}

	@Test
	public void testTransferAmountMustBeNotGreaterThanFromBalance() throws Exception {
		assertTransferFails(fromId, toId, new BigInteger("10"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("0"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("0"));

		depositTo(fromId, new BigInteger("100"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("100"));

		assertTransferFails(fromId, toId, new BigInteger("1000"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("100"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("0"));
	}

	@Test
	public void testTransferShouldIncrementToBalance() throws Exception {
		depositTo(fromId, new BigInteger("100"));
		depositTo(toId, new BigInteger("200"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("100"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("200"));

		BigInteger amount = new BigInteger("20");
		BigInteger toBalance = getBalance(toId);
		transferManager.transfer(fromId, toId, amount);
		assertAccountBalanceEqualsTo(toId, toBalance.add(amount));
	}

	@Test
	public void testTransferShouldDecrementFromBalance() throws Exception {
		depositTo(fromId, new BigInteger("100"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("100"));

		BigInteger amount = new BigInteger("20");
		BigInteger fromBalance = getBalance(fromId);

		transferManager.transfer(fromId, toId, amount);
		assertAccountBalanceEqualsTo(fromId, fromBalance.subtract(amount));
	}

	@Test
	public void testAmountShouldBeOnlyPositive() throws Exception {
		assertTransferFails(fromId, toId, new BigInteger("0"));
		assertTransferFails(fromId, toId, new BigInteger("-10"));
	}

	private BigInteger getBalance(Long accountId) {
		return accountManager.getBalance(accountId);
	}

	private void assertAccountBalanceEqualsTo(Long accountId, BigInteger balance) {
		assertThat(getBalance(accountId), equalTo(balance));
	}

	private void depositTo(Long accountId, BigInteger amount) {
		accountManager.deposit(accountId, amount);
	}

	private void assertTransferFails(Long fromId, Long toId, BigInteger amount) {
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
