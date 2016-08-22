package com.ail.revolut.app.rest;

import com.ail.revolut.app.json.ResponseData;
import com.ail.revolut.app.json.TransferData;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class TransferServiceTest extends BaseServiceTest {

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

	@Test
	public void testTransferAmountMustBeNotGreaterThanFromBallance() throws Exception {
		assertTransferFails(fromId, toId, 10L);
		assertAccountBalanceEqualsTo(fromId, 0L);
		assertAccountBalanceEqualsTo(toId, 0L);

		assertDepositSuccess(fromId, 100L);
		assertAccountBalanceEqualsTo(fromId, 100L);
		assertAccountBalanceEqualsTo(toId, 0L);
		assertTransferFails(fromId, toId, 1000L);
		assertAccountBalanceEqualsTo(fromId, 100L);
		assertAccountBalanceEqualsTo(toId, 0L);
	}

	private void assertTransferSuccess(Long fromId, Long toId, Long amount) {
		ResponseData responseData = performTransfer(fromId, toId, amount);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	private void assertTransferFails(Long fromId, Long toId, Long amount) {
		ResponseData responseData = performTransfer(fromId, toId, amount);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

	private ResponseData performTransfer(Long fromId, Long toId, Long amount) {
		TransferData transferData = new TransferData(fromId, toId, amount);
		ResponseData responseData = getTarget().path("/transfer").request().post(Entity.entity(transferData, MediaType.APPLICATION_JSON), ResponseData.class);
		logResponseData(responseData);
		return responseData;
	}

}

