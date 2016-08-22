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
	public void testCreate() {
		ResponseData responseData = target.path("/account/create").request().put(Entity.entity(new Account(), MediaType.APPLICATION_JSON_TYPE), ResponseData.class);
		logger.info(responseData.toString());

		Long assertAccountId = responseData.getId();
		assertThat(assertAccountId, notNullValue());
		assertThat(responseData.getMessage(), nullValue());

		responseData = target.path("/account/create").request().put(Entity.entity(new Account(), MediaType.APPLICATION_JSON_TYPE), ResponseData.class);
		logger.info(responseData.toString());

		assertThat(responseData.getId(), equalTo(++assertAccountId));
		assertThat(responseData.getMessage(), nullValue());
	}

	@Test
	public void testGetBallance() {
		ResponseData responseData = target.path("/account/create").request().put(Entity.entity(new Account(), MediaType.APPLICATION_JSON_TYPE), ResponseData.class);
		logger.info(responseData.toString());

		Long accountId = responseData.getId();

		assertThat(accountId, notNullValue());
		assertThat(responseData.getMessage(), nullValue());

		responseData = target.path("/account/" + responseData.getId() + "/balance").request().get(ResponseData.class);
		logger.info(responseData.toString());

		assertThat(responseData.getId(), equalTo(0L));
		assertThat(responseData.getMessage(), nullValue());
	}

}

