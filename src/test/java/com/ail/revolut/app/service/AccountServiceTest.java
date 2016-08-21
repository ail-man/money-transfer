package com.ail.revolut.app.service;

import com.ail.revolut.app.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountServiceTest {

	private static Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);

	private AccountService accountService = new AccountServiceImpl();
	private Account account;

	@Before
	public void init() {
		account = accountService.createAccount();
	}

	@Test
	public void testCreateAccount() {
		assertThat(account.getId(), notNullValue());
		assertThat(account.getBalance(), equalTo(0L));
	}

	@Test
	public void testGetBallance() {
		Long ballance = accountService.getBalance(account.getId());
		assertThat(ballance, is(notNullValue()));
	}

	@Test
	public void testDesposit() {
		Long balance;

		accountService.deposit(account.getId(), 100L);
		balance = accountService.getBalance(account.getId());
		assertThat(balance, equalTo(100L));

		accountService.deposit(account.getId(), 23L);
		balance = accountService.getBalance(account.getId());
		assertThat(balance, equalTo(123L));
	}

//	@Test
//	public void testWithdraw() throws Exception {
//		assertThat(accountService.getBalance(account.getId()), equalTo(0L));
//		accountService.deposit(1000L);
//
//		assertThat(accountService.getBalance(account.getId()), equalTo(1000L));
//
//		account.withdraw(10L);
//		assertThat(account.getBalance(), equalTo(990L));
//
//		account.withdraw(123L);
//		assertThat(account.getBalance(), equalTo(867L));
//	}
//
//	@Test
//	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
//		assertThat(accountService.getBalance(account.getId()), equalTo(0L));
//		assertWithdrawFails(account.getId(), 5L);
//
//		account.deposit(30L);
//		assertThat(account.getBalance(), equalTo(30L));
//		assertWithdrawFails(account.getId(), 100L);
//		assertThat(account.getBalance(), equalTo(30L));
//
//		account.withdraw(10L);
//		assertThat(account.getBalance(), equalTo(20L));
//		assertWithdrawFails(account.getId(), 30L);
//		assertThat(account.getBalance(), equalTo(20L));
//
//		account.withdraw(20L);
//		assertThat(account.getBalance(), equalTo(0L));
//		assertWithdrawFails(account.getId(), 1L);
//		assertThat(account.getBalance(), equalTo(0L));
//	}
//
//	private void assertWithdrawFails(long id, long amount) {
//		try {
//			accountService.withdraw(id, amount);
//			fail("Should not withdraw");
//		} catch (Exception e) {
//			assertThat(e, instanceOf(NotEnoughFundsException.class));
//		}
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void testAccountDepositOverflow() {
//		accountService.deposit(account.getId(), Long.MAX_VALUE);
//		accountService.deposit(account.getId(), 1L);
//		account.deposit(1L);
//	}

}
