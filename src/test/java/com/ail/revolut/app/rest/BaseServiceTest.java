package com.ail.revolut.app.rest;

import com.ail.revolut.app.Main;
import com.ail.revolut.app.json.ResponseData;
import com.ail.revolut.app.model.Account;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

	private void logResponseData(ResponseData responseData) {
		logger.info(responseData.toString());
	}

	void assertDepositSuccess(Long accountId, Long depositAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/deposit").request().post(Entity.entity(depositAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	void assertDepositFails(Long accountId, Long depositAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/deposit").request().post(Entity.entity(depositAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

	void assertWithdrawSuccess(Long accountId, Long withdrawAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/withdraw").request().post(Entity.entity(withdrawAmount, MediaType.TEXT_PLAIN), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	void assertWithdrawFails(Long accountId, Long withdrawAmount) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/withdraw").request().post(Entity.entity(withdrawAmount, MediaType.TEXT_PLAIN), ResponseData.class);
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

	void assertAccountBalanceEqualsTo(Long accountId, Long balanceAmount) {
		Long accountBalance = getBallance(accountId);

		assertThat(accountBalance, equalTo(balanceAmount));
	}

	Long getBallance(Long accountId) {
		ResponseData responseData = getTarget().path("/account/" + accountId + "/balance").request().get(ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getMessage(), nullValue());

		Long accountBalance = Long.parseLong(responseData.getValue());
		logger.info("Balance=" + accountBalance);

		return accountBalance;
	}

}
