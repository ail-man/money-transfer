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
		return this.target;
	}

	void logResponseData(ResponseData responseData) {
		logger.info(responseData.toString());
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
		ResponseData responseData = getTarget().path("/account/" + accountId + "/balance").request().get(ResponseData.class);
		logResponseData(responseData);

		Long accountBalance = Long.parseLong(responseData.getValue());
		logger.info("Balance=" + accountBalance);

		assertThat(accountBalance, equalTo(balanceAmount));
		assertThat(responseData.getMessage(), nullValue());
	}
}
