package com.ail.revolut.app.rest;

import java.math.BigDecimal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import com.ail.revolut.app.dto.ResponseData;
import com.ail.revolut.app.dto.TransferData;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Before;
import org.junit.Test;

public class TransferRestServiceTest extends BaseRestServiceTest {

	private Long fromId;
	private Long toId;

	@Before
	public void init() throws Exception {
		fromId = assertCreateAccount();
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("0"));

		toId = assertCreateAccount();
		assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));
	}

	@Test
	public void testTransfer() throws Exception {
		assertDepositSuccess(fromId, new BigDecimal("200"));

		assertAccountBalanceEqualsTo(fromId, new BigDecimal("200"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));

		assertTransferSuccess(fromId, toId, new BigDecimal("10"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("190"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("10"));

		assertTransferSuccess(fromId, toId, new BigDecimal("20"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("170"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("30"));

		assertTransferSuccess(fromId, toId, new BigDecimal("70"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("100"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("100"));
	}

	@Test
	public void testTransferAmountMustBeNotGreaterThanFromBalance() throws Exception {
		assertTransferFails(fromId, toId, new BigDecimal("20"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("0"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));

		assertDepositSuccess(fromId, new BigDecimal("200"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("200"));

		assertTransferFails(fromId, toId, new BigDecimal("1000"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("200"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("0"));
	}

	@Test
	public void testTransferShouldIncrementToBalance() throws Exception {
		assertDepositSuccess(fromId, new BigDecimal("200"));
		assertDepositSuccess(toId, new BigDecimal("300"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("200"));
		assertAccountBalanceEqualsTo(toId, new BigDecimal("300"));

		BigDecimal amount = new BigDecimal("35");
		BigDecimal toBalance = getBalance(toId);

		assertTransferSuccess(fromId, toId, amount);
		assertAccountBalanceEqualsTo(toId, toBalance.add(amount));
	}

	@Test
	public void testTransferShouldDecrementFromBalance() throws Exception {
		assertDepositSuccess(fromId, new BigDecimal("200"));
		assertAccountBalanceEqualsTo(fromId, new BigDecimal("200"));

		BigDecimal amount = new BigDecimal("64");
		BigDecimal fromBalance = getBalance(fromId);

		assertTransferSuccess(fromId, toId, amount);
		assertAccountBalanceEqualsTo(fromId, fromBalance.subtract(amount));
	}

	@Test
	public void testAmountShouldBeOnlyPositive() throws Exception {
		assertTransferFails(fromId, toId, new BigDecimal("0"));
		assertTransferFails(fromId, toId, new BigDecimal("-2"));
	}

	private void assertTransferSuccess(Long fromId, Long toId, BigDecimal amount) {
		ResponseData responseData = performTransfer(fromId, toId, amount);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	private void assertTransferFails(Long fromId, Long toId, BigDecimal amount) {
		ResponseData responseData = performTransfer(fromId, toId, amount);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

	private ResponseData performTransfer(Long fromId, Long toId, BigDecimal amount) {
		TransferData transferData = new TransferData(fromId, toId, amount);
		ResponseData responseData = getTarget().path("/transfer").request().post(Entity.entity(transferData, MediaType.APPLICATION_JSON), ResponseData.class);
		logResponseData(responseData);
		return responseData;
	}

}

