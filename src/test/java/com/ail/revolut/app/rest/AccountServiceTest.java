package com.ail.revolut.app.rest;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AccountServiceTest extends BaseServiceTest {

	private Long accountId;

	@Before
	public void init() throws Exception {
		accountId = assertCreateAccount();
		assertAccountBalanceEqualsTo(accountId, 0L);
	}

	@Test
	public void testCreateAccount() throws Exception {
		Long nextAccountId = assertCreateAccount();
		assertThat(nextAccountId, equalTo(++accountId));
	}

	@Test
	public void testGetBalance() throws Exception {
		Long accountBalance = getBalance(accountId);
		assertThat(accountBalance, is(notNullValue()));
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		assertAccountBalanceEqualsTo(accountId, 0L);
	}

	@Test
	public void testDeposit() throws Exception {
		Long depositAmount = 123L;
		assertDepositSuccess(accountId, depositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);

		Long newDepositAmount = 345L;
		assertDepositSuccess(accountId, newDepositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount + newDepositAmount);
	}

	@Test
	public void testWithdraw() throws Exception {
		Long deposit = 100L;
		assertDepositSuccess(accountId, deposit);
		assertAccountBalanceEqualsTo(accountId, deposit);

		Long withdrawAmount = 13L;
		assertWithdrawSuccess(accountId, withdrawAmount);
		assertAccountBalanceEqualsTo(accountId, deposit - withdrawAmount);

		Long newWithdrawAmount = 25L;
		assertWithdrawSuccess(accountId, newWithdrawAmount);

		assertWithdrawSuccess(accountId, deposit - (withdrawAmount + newWithdrawAmount));
	}

	@Test
	public void testAmountMustBePositiveOnly() throws Exception {
		Long zeroAmount = 0L;
		assertDepositFails(accountId, zeroAmount);
		assertAccountBalanceEqualsTo(accountId, zeroAmount);

		Long negativeAmount = -10L;

		assertDepositFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, 0L);

		assertWithdrawFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, 0L);

		Long successDepositAmount = 100L;
		assertDepositSuccess(accountId, successDepositAmount);
		assertAccountBalanceEqualsTo(accountId, successDepositAmount);

		assertDepositFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, successDepositAmount);

		assertWithdrawFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, successDepositAmount);
	}

	@Test
	public void testWithdrawMustNotBeGreaterThanDeposit() throws Exception {
		assertWithdrawFails(accountId, 0L);
		assertAccountBalanceEqualsTo(accountId, 0L);

		Long depositAmount = 10L;
		assertDepositSuccess(accountId, depositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);

		Long withdrawAmount = 20L;
		assertThat(withdrawAmount, greaterThan(depositAmount));

		assertWithdrawFails(accountId, withdrawAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);
	}

	@Test
	public void testAccountDepositOverflow() throws Exception {
		Long max = Long.MAX_VALUE;
		assertDepositSuccess(accountId, Long.MAX_VALUE);
		assertAccountBalanceEqualsTo(accountId, Long.MAX_VALUE);

		assertDepositFails(accountId, max + 1L);
		assertAccountBalanceEqualsTo(accountId, Long.MAX_VALUE);
	}

}

