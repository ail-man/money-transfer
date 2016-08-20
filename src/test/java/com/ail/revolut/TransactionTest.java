package com.ail.revolut;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TransactionTest {

	@Test
	public void testCommit() {
		Account from = new Account(1L);
		from.deposit(100L);

		Account to = new Account(2L);
		assertThat(to.getBallance(), equalTo(0L));

		Transaction transaction = new Transaction(from, to, 10L);
		transaction.commit();
		assertThat(from.getBallance(), equalTo(90L));
		assertThat(to.getBallance(), equalTo(10L));
	}
}
