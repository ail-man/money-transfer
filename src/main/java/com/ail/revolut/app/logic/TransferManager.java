package com.ail.revolut.app.logic;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface TransferManager {

	void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) throws NotEnoughFundsException;
}
