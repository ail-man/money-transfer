package com.ail.revolut.app.rest;

import com.ail.revolut.MoneyTransferApp;
import com.ail.revolut.app.model.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class AccountRestServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountRestServiceTest.class);

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		server = MoneyTransferApp.startServer();
		Client c = ClientBuilder.newClient();
		target = c.target(MoneyTransferApp.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	@Test
	public void testGetIt() {
		WebTarget webTarget = target.path("/account/create");
		javax.ws.rs.core.Response response = webTarget.request().get();
		logger.info(response.toString());
//
//		Response responseMsg = target.path("/account/create").request().get(Response.class);
//		logger.info(responseMsg.toString());
	}
}

