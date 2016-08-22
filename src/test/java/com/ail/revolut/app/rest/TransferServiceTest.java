package com.ail.revolut.app.rest;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class TransferServiceTest extends BaseServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(TransferServiceTest.class);

	@Test
	@Ignore
	public void testGetIt() {
		String responseMsg = getTarget().path("transfer").request().get(String.class);
		assertEquals("Got it!", responseMsg);
	}

}

