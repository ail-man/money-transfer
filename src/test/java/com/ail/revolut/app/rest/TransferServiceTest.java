package com.ail.revolut.app.rest;

import java.math.BigInteger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import com.ail.revolut.app.dto.ResponseData;
import com.ail.revolut.app.dto.TransferData;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Before;
import org.junit.Test;

public class TransferServiceTest extends BaseServiceTest {

	private Long fromId;
	private Long toId;

	@Before
	public void init() throws Exception {
		fromId = assertCreateAccount();
		assertAccountBalanceEqualsTo(fromId, new BigInteger("0"));

		toId = assertCreateAccount();
		assertAccountBalanceEqualsTo(toId, new BigInteger("0"));
	}

	@Test
	public void testTransfer() throws Exception {
		assertDepositSuccess(fromId, new BigInteger("200"));

		assertAccountBalanceEqualsTo(fromId, new BigInteger("200"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("0"));

		assertTransferSuccess(fromId, toId, new BigInteger("10"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("190"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("10"));

		assertTransferSuccess(fromId, toId, new BigInteger("20"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("170"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("30"));

		assertTransferSuccess(fromId, toId, new BigInteger("70"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("100"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("100"));
	}

	@Test
	public void testTransferAmountMustBeNotGreaterThanFromBalance() throws Exception {
		assertTransferFails(fromId, toId, new BigInteger("20"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("0"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("0"));

		assertDepositSuccess(fromId, new BigInteger("200"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("200"));

		assertTransferFails(fromId, toId, new BigInteger("1000"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("200"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("0"));
	}

	@Test
	public void testTransferShouldIncrementToBalance() throws Exception {
		assertDepositSuccess(fromId, new BigInteger("200"));
		assertDepositSuccess(toId, new BigInteger("300"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("200"));
		assertAccountBalanceEqualsTo(toId, new BigInteger("300"));

		BigInteger amount = new BigInteger("35");
		BigInteger toBalance = getBalance(toId);

		assertTransferSuccess(fromId, toId, amount);
		assertAccountBalanceEqualsTo(toId, toBalance.add(amount));
	}

	@Test
	public void testTransferShouldDecrementFromBalance() throws Exception {
		assertDepositSuccess(fromId, new BigInteger("200"));
		assertAccountBalanceEqualsTo(fromId, new BigInteger("200"));

		BigInteger amount = new BigInteger("64");
		BigInteger fromBalance = getBalance(fromId);

		assertTransferSuccess(fromId, toId, amount);
		assertAccountBalanceEqualsTo(fromId, fromBalance.subtract(amount));
	}

	@Test
	public void testAmountShouldBeOnlyPositive() throws Exception {
		assertTransferFails(fromId, toId, new BigInteger("0"));
		assertTransferFails(fromId, toId, new BigInteger("-2"));
	}

	private void assertTransferSuccess(Long fromId, Long toId, BigInteger amount) {
		ResponseData responseData = performTransfer(fromId, toId, amount);

		assertThat(responseData.getValue(), notNullValue());
		assertThat(responseData.getMessage(), nullValue());
	}

	private void assertTransferFails(Long fromId, Long toId, BigInteger amount) {
		ResponseData responseData = performTransfer(fromId, toId, amount);

		assertThat(responseData.getValue(), nullValue());
		assertThat(responseData.getMessage(), notNullValue());
	}

	private ResponseData performTransfer(Long fromId, Long toId, BigInteger amount) {
		TransferData transferData = new TransferData(fromId, toId, amount);
		ResponseData responseData = getTarget().path("/transfer").request().post(Entity.entity(transferData, MediaType.APPLICATION_JSON), ResponseData.class);
		logResponseData(responseData);
		return responseData;
	}

}

