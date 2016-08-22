package com.ail.revolut.app.rest;

import com.ail.revolut.app.Main;
import com.ail.revolut.app.json.ResponseData;
import com.ail.revolut.app.model.Account;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AccountRestServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountRestServiceTest.class);

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		server = Main.startServer();
		Client c = ClientBuilder.newClient();
		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	@Test
	public void testCreateAccount() {
		Long accountId = createAccount();
		Long nextAccountId = createAccount();
		assertThat(nextAccountId, equalTo(++accountId));
	}

	@Test
	public void testGetBallance() {
		Long accountId = createAccount();
		assertAccountBalanceEqualsTo(accountId, 0L);
	}

	@Test
	public void testDeposit() {
		Long accountId = createAccount();
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
		Long accountId = createAccount();
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
		Long accountId = createAccount();
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
		Long accountId = createAccount();
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

	private void logResponseData(ResponseData responseData) {
		logger.info(responseData.toString());
	}

	private Long createAccount() {
		ResponseData responseData = target.path("/account/create").request().put(Entity.entity(new Account(), MediaType.APPLICATION_JSON_TYPE), ResponseData.class);
		logResponseData(responseData);

		Long accountId = Long.parseLong(responseData.getValue());
		assertThat(accountId, notNullValue());
		assertThat(responseData.getMessage(), nullValue());
		return accountId;
	}

	private void assertAccountBalanceEqualsTo(Long accountId, Long balanceAmount) {
		ResponseData responseData = target.path("/account/" + accountId + "/balance").request().get(ResponseData.class);
		logResponseData(responseData);

		Long accountBalance = Long.parseLong(responseData.getValue());
		logger.info("Balance=" + accountBalance);

		assertThat(accountBalance, equalTo(balanceAmount));
		assertThat(responseData.getMessage(), nullValue());
	}

	private void assertDepositSuccess(Long accountId, Long depositAmount) {
		ResponseData responseData = target.path("/account/" + accountId + "/deposit").request().post(Entity.entity(depositAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	private void assertDepositFails(Long accountId, Long depositAmount) {
		ResponseData responseData = target.path("/account/" + accountId + "/deposit").request().post(Entity.entity(depositAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

	private void assertWithdrawSuccess(Long accountId, Long withdrawAmount) {
		ResponseData responseData = target.path("/account/" + accountId + "/withdraw").request().post(Entity.entity(withdrawAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	private void assertWithdrawFails(Long accountId, Long withdrawAmount) {
		ResponseData responseData = target.path("/account/" + accountId + "/withdraw").request().post(Entity.entity(withdrawAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

}

