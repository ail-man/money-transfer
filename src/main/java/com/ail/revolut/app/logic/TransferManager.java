package com.ail.revolut.app.logic;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface TransferManager {

	void transfer(Long fromAccountId, Long toAccountId, Long amount) throws NotEnoughFundsException;
}
