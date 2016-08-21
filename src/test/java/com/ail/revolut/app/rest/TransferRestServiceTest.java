package com.ail.revolut.app.rest;

import com.ail.revolut.MoneyTransferApp;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;

public class TransferRestServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(TransferRestServiceTest.class);

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
		String responseMsg = target.path("transfer").request().get(String.class);
		assertEquals("Got it!", responseMsg);
	}
}

