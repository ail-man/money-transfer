package com.ail.revolut.app.rest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferServiceTest extends BaseServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(TransferServiceTest.class);

	private Long fromId;
	private Long toId;

	@Before
	public void init() throws Exception {
		fromId = assertCreateAccount();
		assertAccountBalanceEqualsTo(fromId, 0L);

		toId = assertCreateAccount();
		assertAccountBalanceEqualsTo(toId, 0L);
	}

	@Test
	public void testGetIt() throws Exception {
		logger.info(fromId + "");
		logger.info(toId + "");
	}

}

