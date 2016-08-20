package com.ail.revolut.app;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.fail;

public class AccountTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountTest.class);

	@Test
	public void testNewAccountHasZeroBallance() {
		Account account = new Account(12345L);
		assertThat(account.getBallance(), equalTo(0L));
	}

	@Test
	public void testDesposit() {
		Account account = new Account(12345L);

		account.deposit(100L);
		assertThat(account.getBallance(), equalTo(100L));

		account.deposit(23L);
		assertThat(account.getBallance(), equalTo(123L));
	}

	@Test
	public void testWithdraw() throws Exception {
		Account account = new Account(12345L);
		account.deposit(1000L);

		assertThat(account.getBallance(), equalTo(1000L));

		account.withdraw(10L);
		assertThat(account.getBallance(), equalTo(990L));

		account.withdraw(123L);
		assertThat(account.getBallance(), equalTo(867L));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBallance() throws Exception {
		Account account = new Account(12345L);

		assertThat(account.getBallance(), equalTo(0L));
		assertWithdrawFails(account, 5L);

		account.deposit(30L);
		assertThat(account.getBallance(), equalTo(30L));
		assertWithdrawFails(account, 100L);
		assertThat(account.getBallance(), equalTo(30L));

		account.withdraw(10L);
		assertThat(account.getBallance(), equalTo(20L));
		assertWithdrawFails(account, 30L);
		assertThat(account.getBallance(), equalTo(20L));

		account.withdraw(20L);
		assertThat(account.getBallance(), equalTo(0L));
		assertWithdrawFails(account, 1L);
		assertThat(account.getBallance(), equalTo(0L));
	}

	private void assertWithdrawFails(Account account, long amount) {
		try {
			account.withdraw(amount);
			fail("Should not withdraw");
		} catch (Exception e) {
			assertThat(e, instanceOf(NotEnoughFundsException.class));
		}
	}

	@Test(expected = RuntimeException.class)
	public void testAccountDepositOverflow() {
		Account account = new Account(12345L);
		account.deposit(Long.MAX_VALUE);

		assertThat(account.getBallance(), equalTo(Long.MAX_VALUE));
		account.deposit(1L);
	}


}
