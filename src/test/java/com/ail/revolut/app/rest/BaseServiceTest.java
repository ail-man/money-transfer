package com.ail.revolut.app.rest;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.ail.revolut.app.Main;
import com.ail.revolut.app.dto.Money;
import com.ail.revolut.app.dto.ResponseData;
import com.ail.revolut.app.model.Account;
import org.glassfish.grizzly.http.server.HttpServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BaseServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceTest.class);

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

	WebTarget getTarget() {
		return target;
	}

	void logResponseData(ResponseData responseData) {
		logger.info(responseData.toString());
	}

	void assertDepositSuccess(Long accountId, BigDecimal depositAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/deposit").request().post(Entity.entity(new Money(depositAmount), MediaType.APPLICATION_JSON), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	void assertDepositFails(Long accountId, BigDecimal depositAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/deposit").request().post(Entity.entity(new Money(depositAmount), MediaType.APPLICATION_JSON), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

	void assertWithdrawSuccess(Long accountId, BigDecimal withdrawAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/withdraw").request().post(Entity.entity(new Money(withdrawAmount), MediaType.APPLICATION_JSON), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	void assertWithdrawFails(Long accountId, BigDecimal withdrawAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/withdraw").request().post(Entity.entity(new Money(withdrawAmount), MediaType.APPLICATION_JSON), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

	Long assertCreateAccount() {
		ResponseData responseData = getTarget().path("/account/create").request().put(Entity.entity(new Account(), MediaType.APPLICATION_JSON_TYPE), ResponseData.class);
		logResponseData(responseData);

		Long accountId = Long.parseLong(responseData.getValue());
		assertThat(accountId, notNullValue());
		assertThat(responseData.getMessage(), nullValue());
		return accountId;
	}

	void assertAccountBalanceEqualsTo(Long accountId, BigDecimal balanceAmount) {
		BigDecimal accountBalance = getBalance(accountId);

		assertThat(accountBalance.compareTo(balanceAmount), equalTo(0));
	}

	BigDecimal getBalance(Long accountId) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/balance").request().get(ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getMessage(), nullValue());

		BigDecimal accountBalance = new BigDecimal(responseData.getValue());
		logger.info("Balance={}", accountBalance);

		return accountBalance;
	}

}
