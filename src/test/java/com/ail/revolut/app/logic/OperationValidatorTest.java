package com.ail.revolut.app.logic;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

// TODO may be not need
public class OperationValidatorTest {

	@Test
	public void testValidateDepositOperation() {
		assertThat(OperationValidator.validateDepositOperation(0L, 10L), equalTo(true));
		assertThat(OperationValidator.validateDepositOperation(0L, 0L), equalTo(false));
		assertThat(OperationValidator.validateDepositOperation(0L, 0L), equalTo(false));
	}

	@Test
	public void testValidateWithdrawOperation() {
		OperationValidator.validateWithdrawOperation(0L, 10L);
	}

	@Test
	public void testValidateTransferOperation() {
		OperationValidator.validateTransferOperation(0L, 0L, 10L);
	}
}
