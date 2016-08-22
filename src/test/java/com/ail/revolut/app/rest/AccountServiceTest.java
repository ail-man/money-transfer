package com.ail.revolut.app.rest;

import com.ail.revolut.app.json.ResponseData;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AccountServiceTest extends BaseServiceTest {

	@Test
	public void testCreateAccount() {
		Long accountId = assertCreateAccount();
		Long nextAccountId = assertCreateAccount();
		assertThat(nextAccountId, equalTo(++accountId));
	}

	@Test
	public void testGetBallance() {
		Long accountId = assertCreateAccount();
		assertAccountBalanceEqualsTo(accountId, 0L);
	}

	@Test
	public void testDeposit() {
		Long accountId = assertCreateAccount();
		assertAccountBalanceEqualsTo(accountId, 0L);

		Long depositAmount = 123L;
		assertDepositSuccess(accountId, depositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount);

		Long newDepositAmount = 345L;
		assertDepositSuccess(accountId, newDepositAmount);
		assertAccountBalanceEqualsTo(accountId, depositAmount + newDepositAmount);
	}

	@Test
	public void testWithdraw() {
		Long accountId = assertCreateAccount();
		assertAccountBalanceEqualsTo(accountId, 0L);

		Long deposit = 100L;
		assertDepositSuccess(accountId, deposit);
		assertAccountBalanceEqualsTo(accountId, deposit);

		Long withdrawAmount = 13L;
		assertWithdrawSuccess(accountId, withdrawAmount);
		assertAccountBalanceEqualsTo(accountId, deposit - withdrawAmount);

		Long newWithdrawAmount = 25L;
		assertWithdrawSuccess(accountId, newWithdrawAmount);

		assertWithdrawSuccess(accountId, deposit - withdrawAmount - newWithdrawAmount);
	}

	@Test
	public void testAmountMustBePositiveOnly() {
		Long accountId = assertCreateAccount();
		assertAccountBalanceEqualsTo(accountId, 0L);

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
	public void testWithdrawMustNotBeGreaterThanDeposit() {
		Long accountId = assertCreateAccount();
		assertAccountBalanceEqualsTo(accountId, 0L);

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

	private void assertDepositSuccess(Long accountId, Long depositAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/deposit").request().post(Entity.entity(depositAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	private void assertDepositFails(Long accountId, Long depositAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/deposit").request().post(Entity.entity(depositAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

	private void assertWithdrawSuccess(Long accountId, Long withdrawAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/withdraw").request().post(Entity.entity(withdrawAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	private void assertWithdrawFails(Long accountId, Long withdrawAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/withdraw").request().post(Entity.entity(withdrawAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

}

