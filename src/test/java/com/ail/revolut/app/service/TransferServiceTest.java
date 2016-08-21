package com.ail.revolut.app.service;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TransferServiceTest {

	private Long fromAccountId;
	private Long toAccountId;

	@Before
	public void init() {
		AccountService accountService = new AccountServiceImpl();
		fromAccountId = accountService.createAccount();
//
//		fromAccountId.deposit(100L);
//		assertThat(fromAccountId.getBalance(), equalTo(100L));
//
//		toAccountId = accountService.createAccount();
//		assertThat(toAccountId.getBalance(), equalTo(0L));
	}

	@Test
	public void test() {
		TransferService transferService = new TransferServiceImpl();

//		long amount = 11L;
//
//		transferService.transfer(fromAccountId, toAccountId, amount);
//
//		assertThat(fromAccountId.getBalance(), equalTo(89L));
//		assertThat(toAccountId.getBalance(), equalTo(11L));
	}
}
