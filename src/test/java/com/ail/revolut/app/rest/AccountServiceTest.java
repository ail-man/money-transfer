package com.ail.revolut.app.rest;

import java.math.BigInteger;

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
		assertAccountBalanceEqualsTo(accountId, new BigInteger("0"));
	}

	@Test
	public void testCreateAccount() throws Exception {
		Long nextAccountId = assertCreateAccount();
		assertThat(nextAccountId, equalTo(++accountId));
	}

	@Test
	public void testGetBalance() throws Exception {
		BigInteger accountBalance = getBalance(accountId);
		assertThat(accountBalance, is(notNullValue()));
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		assertAccountBalanceEqualsTo(accountId, new BigInteger("0"));
	}

	@Test
	public void testDeposit() throws Exception {
		BigInteger depositAmount = new BigInteger("123");
		assertDepositSuccess(accountId, depositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);

		BigInteger newDepositAmount = new BigInteger("345");
		assertDepositSuccess(accountId, newDepositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount.add(newDepositAmount));
	}

	@Test
	public void testWithdraw() throws Exception {
		BigInteger deposit = new BigInteger("100");
		assertDepositSuccess(accountId, deposit);
		assertAccountBalanceEqualsTo(accountId, deposit);

		BigInteger withdrawAmount = new BigInteger("13");
		assertWithdrawSuccess(accountId, withdrawAmount);
		assertAccountBalanceEqualsTo(accountId, deposit.subtract(withdrawAmount));

		BigInteger newWithdrawAmount = new BigInteger("25");
		assertWithdrawSuccess(accountId, newWithdrawAmount);

		assertWithdrawSuccess(accountId, deposit.subtract(withdrawAmount.add(newWithdrawAmount)));
	}

	@Test
	public void testAmountMustBePositiveOnly() throws Exception {
		BigInteger zeroAmount = new BigInteger("0");
		assertDepositFails(accountId, zeroAmount);
		assertAccountBalanceEqualsTo(accountId, zeroAmount);

		BigInteger negativeAmount = new BigInteger("-10");

		assertDepositFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, new BigInteger("0"));

		assertWithdrawFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, new BigInteger("0"));

		BigInteger successDepositAmount = new BigInteger("100");
		assertDepositSuccess(accountId, successDepositAmount);
		assertAccountBalanceEqualsTo(accountId, successDepositAmount);

		assertDepositFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, successDepositAmount);

		assertWithdrawFails(accountId, negativeAmount);
		assertAccountBalanceEqualsTo(accountId, successDepositAmount);
	}

	@Test
	public void testWithdrawMustNotBeGreaterThanDeposit() throws Exception {
		assertWithdrawFails(accountId, new BigInteger("0"));
		assertAccountBalanceEqualsTo(accountId, new BigInteger("0"));

		BigInteger depositAmount = new BigInteger("10");
		assertDepositSuccess(accountId, depositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);

		BigInteger withdrawAmount = new BigInteger("20");
		assertThat(withdrawAmount, greaterThan(depositAmount));

		assertWithdrawFails(accountId, withdrawAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);
	}

}

