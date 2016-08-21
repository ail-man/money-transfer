package com.ail.revolut.app.service;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TransferServiceTest {

	private TransferService transferService = new TransferServiceImpl();
	private AccountService accountService = new AccountServiceImpl();

	private Long fromAccountId;
	private Long toAccountId;

	@Before
	public void init() {
		fromAccountId = accountService.createAccount();
		toAccountId = accountService.createAccount();
	}

	@Test
	public void testTransfer() {
		transferService.transfer(fromAccountId, toAccountId, 100L);
	}

	@Test
	public void testTransferShouldIncrementToAccountBalance() {
		assertThat(accountService.getBalance(toAccountId), equalTo(0L));

		Long amount = 100L;
		transferService.transfer(fromAccountId, toAccountId, amount);
		assertThat(accountService.getBalance(toAccountId), equalTo(amount));
	}

	@Test
	public void testTransferShouldDecrementFromAccountBalance() {
		assertThat(accountService.getBalance(fromAccountId), equalTo(0L));
		accountService.deposit(fromAccountId, 100L);
		assertThat(accountService.getBalance(fromAccountId), equalTo(100L));

		Long amount = 20L;

		transferService.transfer(fromAccountId, toAccountId, amount);
		assertThat(accountService.getBalance(fromAccountId), equalTo(80L));
	}
}
