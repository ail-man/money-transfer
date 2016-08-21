package com.ail.revolut.app.service;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

public class TransferServiceTest {

	private TransferService transferService = new TransferServiceImpl();
	private AccountService accountService = new AccountServiceImpl();

	private Long fromId;
	private Long toId;

	@Before
	public void init() {
		fromId = accountService.createAccount();
		toId = accountService.createAccount();
	}

	@Test
	public void testTransfer() throws Exception {
		accountService.deposit(fromId, 100L);

		assertThat(accountService.getBalance(fromId), equalTo(100L));
		assertThat(accountService.getBalance(toId), equalTo(0L));

		transferService.transfer(fromId, toId, 10L);
		assertThat(accountService.getBalance(fromId), equalTo(90L));
		assertThat(accountService.getBalance(toId), equalTo(10L));

		transferService.transfer(fromId, toId, 20L);
		assertThat(accountService.getBalance(fromId), equalTo(70L));
		assertThat(accountService.getBalance(toId), equalTo(30L));

		transferService.transfer(fromId, toId, 70L);
		assertThat(accountService.getBalance(fromId), equalTo(0L));
		assertThat(accountService.getBalance(toId), equalTo(100L));
	}

	@Test
	public void testTransferAmountMustBeNotGreaterThanFromBallance() {
		assertThat(accountService.getBalance(fromId), equalTo(0L));
		assertThat(accountService.getBalance(toId), equalTo(0L));
		assertTransferFails(fromId, toId, 10L);
		assertThat(accountService.getBalance(fromId), equalTo(0L));
		assertThat(accountService.getBalance(toId), equalTo(0L));

		accountService.deposit(fromId, 100L);
		assertThat(accountService.getBalance(fromId), equalTo(100L));
		assertThat(accountService.getBalance(toId), equalTo(0L));
		assertTransferFails(fromId, toId, 1000L);
		assertThat(accountService.getBalance(fromId), equalTo(100L));
		assertThat(accountService.getBalance(toId), equalTo(0L));
	}

	@Test
	public void testTransferToBalanceOverflow() throws Exception {
		assertThat(accountService.getBalance(fromId), equalTo(0L));
		assertThat(accountService.getBalance(toId), equalTo(0L));

		accountService.deposit(fromId, 1L);
		accountService.deposit(toId, Long.MAX_VALUE);
		assertThat(accountService.getBalance(fromId), equalTo(1L));
		assertThat(accountService.getBalance(toId), equalTo(Long.MAX_VALUE));

		try {
			transferService.transfer(fromId, toId, 1L);
			fail("Should not transfer");
		} catch (RuntimeException e) {
			assertThat(e.getMessage(), is(notNullValue()));
		}

		assertThat(accountService.getBalance(fromId), equalTo(1L));
		assertThat(accountService.getBalance(toId), equalTo(Long.MAX_VALUE));
	}

	@Test
	public void testTransferShouldIncrementToBalance() throws Exception {
		accountService.deposit(fromId, 100L);
		accountService.deposit(toId, 200L);
		assertThat(accountService.getBalance(fromId), equalTo(100L));
		assertThat(accountService.getBalance(toId), equalTo(200L));

		Long amount = 20L;
		Long toBalance = accountService.getBalance(toId);
		transferService.transfer(fromId, toId, amount);
		assertThat(accountService.getBalance(toId), equalTo(toBalance + amount));
	}

	@Test
	public void testTransferShouldDecrementFromBalance() throws Exception {
		assertThat(accountService.getBalance(fromId), equalTo(0L));
		accountService.deposit(fromId, 100L);
		assertThat(accountService.getBalance(fromId), equalTo(100L));

		Long amount = 20L;

		transferService.transfer(fromId, toId, amount);
		assertThat(accountService.getBalance(fromId), equalTo(80L));
	}

	@Test
	public void testAmountShouldBeOnlyPositive() {
		assertTransferFails(1L, 2L, 0L);
		assertTransferFails(1L, 2L, -10L);
	}

	private void assertTransferFails(Long fromId, Long toId, Long amount) {
		try {
			transferService.transfer(fromId, toId, amount);
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
