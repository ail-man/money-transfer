package com.ail.revolut.app.logic;

import java.math.BigInteger;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface TransferManager {

	void transfer(Long fromAccountId, Long toAccountId, BigInteger amount) throws NotEnoughFundsException;
}
