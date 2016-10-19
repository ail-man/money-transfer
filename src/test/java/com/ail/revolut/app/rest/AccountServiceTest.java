package com.ail.revolut.app.rest;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceTest extends BaseServiceTest {

	private Long accountId;

	@Before
	public void init() throws Exception {
		accountId = assertCreateAccount();
		assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));
	}

	@Test
	public void testCreateAccount() throws Exception {
		Long nextAccountId = assertCreateAccount();
		assertThat(nextAccountId, equalTo(++accountId));
	}

	@Test
	public void testGetBalance() throws Exception {
		BigDecimal accountBalance = getBalance(accountId);
		assertThat(accountBalance, is(notNullValue()));
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));
	}

	@Test
	public void testDeposit() throws Exception {
		BigDecimal depositAmount = new BigDecimal("123");
		assertDepositSuccess(accountId, depositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);

		BigDecimal newDepositAmount = new BigDecimal("345");
		assertDepositSuccess(accountId, newDepositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount.add(newDepositAmount));
	}

	@Test
	public void testWithdraw() throws Exception {
		BigDecimal deposit = new BigDecimal("100");
		assertDepositSuccess(accountId, deposit);
		assertAccountBalanceEqualsTo(accountId, deposit);

		BigDecimal withdrawAmount = new BigDecimal("13");
		assertWithdrawSuccess(accountId, withdrawAmount);
		assertAccountBalanceEqualsTo(accountId, deposit.subtract(withdrawAmount));

		BigDecimal newWithdrawAmount = new BigDecimal("25");
		assertWithdrawSuccess(accountId, newWithdrawAmount);

		assertWithdrawSuccess(accountId, deposit.subtract(withdrawAmount.add(newWithdrawAmount)));
	}

	@Test
	public void testAmountMustBePositiveOnly() throws Exception {
		BigDecimal zeroAmount = new BigDecimal("0");
		assertDepositFails(accountId, zeroAmount);
		assertAccountBalanceEqualsTo(accountId, zeroAmount);

		BigDecimal negativeAmount = new BigDecimal("-10");

		assertDepositFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));

		assertWithdrawFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));

		BigDecimal successDepositAmount = new BigDecimal("100");
		assertDepositSuccess(accountId, successDepositAmount);
		assertAccountBalanceEqualsTo(accountId, successDepositAmount);

		assertDepositFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, successDepositAmount);

		assertWithdrawFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, successDepositAmount);
	}

	@Test
	public void testWithdrawMustNotBeGreaterThanDeposit() throws Exception {
		assertWithdrawFails(accountId, new BigDecimal("0"));
		assertAccountBalanceEqualsTo(accountId, new BigDecimal("0"));

		BigDecimal depositAmount = new BigDecimal("10");
		assertDepositSuccess(accountId, depositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);

		BigDecimal withdrawAmount = new BigDecimal("20");
		assertThat(withdrawAmount, greaterThan(depositAmount));

		assertWithdrawFails(accountId, withdrawAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);
	}

}

