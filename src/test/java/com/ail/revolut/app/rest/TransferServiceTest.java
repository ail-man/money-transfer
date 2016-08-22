package com.ail.revolut.app.rest;

import com.ail.revolut.app.json.ResponseData;
import com.ail.revolut.app.json.TransferData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

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
	public void testTransfer() throws Exception {
		assertDepositSuccess(fromId, 100L);

		assertAccountBalanceEqualsTo(fromId, 100L);
		assertAccountBalanceEqualsTo(toId, 0L);

		assertTransferSuccess(fromId, toId, 10L);
		assertAccountBalanceEqualsTo(fromId, 90L);
		assertAccountBalanceEqualsTo(toId, 10L);

		assertTransferSuccess(fromId, toId, 20L);
		assertAccountBalanceEqualsTo(fromId, 70L);
		assertAccountBalanceEqualsTo(toId, 30L);

		assertTransferSuccess(fromId, toId, 70L);
		assertAccountBalanceEqualsTo(fromId, 0L);
		assertAccountBalanceEqualsTo(toId, 100L);
	}

	private void assertTransferSuccess(Long fromId, Long toId, Long amount) {
		TransferData transferData = new TransferData(fromId, toId, amount);
		ResponseData responseData = getTarget().path("/transfer").request().post(Entity.entity(transferData, MediaType.APPLICATION_JSON), ResponseData.class);
		logResponseData(responseData);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

}

