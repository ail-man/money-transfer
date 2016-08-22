package com.ail.revolut.app.rest;

import com.ail.revolut.app.Main;
import com.ail.revolut.app.json.ResponseData;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
		ResponseData responseData = target.path("/account/create").request().get(ResponseData.class);

		logger.debug(responseData.toString());

		long assertAccountId = 1L;
		assertThat(responseData.getId(), equalTo(assertAccountId));
		assertThat(responseData.getMessage(), equalTo(null));

		responseData = target.path("/account/create").request().get(ResponseData.class);

		assertThat(responseData.getId(), equalTo(++assertAccountId));
		assertThat(responseData.getMessage(), equalTo(null));
	}
}

