package com.ail.revolut.app.service;

import com.ail.revolut.app.NotEnoughFundsException;
import com.ail.revolut.app.model.Account;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;

public class AccountServiceTest {

	private static Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);

	private AccountService accountService = new AccountServiceImpl();
	private Account account;

	@Test
	public void testCreateAccount() {
		account = accountService.createAccount();
		assertThat(account.getId(), notNullValue());
	}

	@Test
	public void testGetBallance() {
		account = accountService.createAccount();
		Long id = account.getId();
		assertThat(id, notNullValue());

		assertThat(accountService.getBalance(id), is(notNullValue()));
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() {
		account = accountService.createAccount();
		Long id = account.getId();
		assertThat(id, notNullValue());

		assertThat(accountService.getBalance(id), equalTo(0L));
	}

	@Test
	public void testDesposit() {
		account = accountService.createAccount();
		Long id = account.getId();
		assertThat(id, notNullValue());

		assertThat(accountService.getBalance(id), equalTo(0L));

		accountService.deposit(id, 100L);
		assertThat(accountService.getBalance(id), equalTo(100L));

		accountService.deposit(id, 23L);
		assertThat(accountService.getBalance(id), equalTo(123L));
	}

	@Test
	public void testWithdraw() throws Exception {
		account = accountService.createAccount();
		Long id = account.getId();
		assertThat(id, notNullValue());

		assertThat(accountService.getBalance(id), equalTo(0L));

		accountService.deposit(id, 1000L);
		assertThat(accountService.getBalance(id), equalTo(1000L));

		accountService.withdraw(id, 10L);
		assertThat(accountService.getBalance(id), equalTo(990L));

		accountService.withdraw(id, 123L);
		assertThat(accountService.getBalance(id), equalTo(867L));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		account = accountService.createAccount();
		Long id = account.getId();
		assertThat(id, notNullValue());

		assertThat(accountService.getBalance(id), equalTo(0L));
		assertWithdrawFails(id, 5L);

		accountService.deposit(id, 30L);
		assertThat(accountService.getBalance(id), equalTo(30L));
		assertWithdrawFails(id, 100L);
		assertThat(accountService.getBalance(id), equalTo(30L));

		accountService.withdraw(id, 10L);
		assertThat(accountService.getBalance(id), equalTo(20L));
		assertWithdrawFails(id, 30L);
		assertThat(accountService.getBalance(id), equalTo(20L));

		accountService.withdraw(id, 20L);
		assertThat(accountService.getBalance(id), equalTo(0L));
		assertWithdrawFails(id, 1L);
		assertThat(accountService.getBalance(id), equalTo(0L));
	}

	private void assertWithdrawFails(Long id, Long amount) {
		try {
			accountService.withdraw(id, amount);
			fail("Should not withdraw");
		} catch (Exception e) {
			assertThat(e, instanceOf(NotEnoughFundsException.class));
		}
	}

//	@Test(expected = RuntimeException.class)
//	public void testAccountDepositOverflow() {
//		accountService.deposit(account.getId(), Long.MAX_VALUE);
//		accountService.deposit(account.getId(), 1L);
//		account.deposit(1L);
//	}

}
