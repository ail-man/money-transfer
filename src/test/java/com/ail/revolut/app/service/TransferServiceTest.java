package com.ail.revolut.app.service;

import com.ail.revolut.app.model.Account;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TransferServiceTest {

	@Test
	public void test() {
		TransferService transferService = new TransferServiceImpl();

		Account from = new Account();
		from.setBallance(100L);

		Account to = new Account();
		to.setBallance(0L);

		long amount = 11L;

		transferService.transfer(from, to, amount);

		assertThat(from.getBallance(), equalTo(89L));
		assertThat(to.getBallance(), equalTo(11L));
	}
}
