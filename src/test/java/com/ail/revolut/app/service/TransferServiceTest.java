package com.ail.revolut.app.service;

import com.ail.revolut.app.logic.IAccount;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TransferServiceTest {

	private IAccount from;
	private IAccount to;

	@Before
	public void init() {
		AccountService accountService = new AccountServiceImpl();
		from = accountService.createAccount();
		from.deposit(100L);
		assertThat(from.getBallance(), equalTo(100L));

		to = accountService.createAccount();
		assertThat(to.getBallance(), equalTo(0L));
	}

	@Test
	public void test() {
		TransferService transferService = new TransferServiceImpl();

		long amount = 11L;

		transferService.transfer(from, to, amount);

		assertThat(from.getBallance(), equalTo(89L));
		assertThat(to.getBallance(), equalTo(11L));
	}
}
