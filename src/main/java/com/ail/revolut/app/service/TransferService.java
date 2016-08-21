package com.ail.revolut.app.service;

import com.ail.revolut.app.NotEnoughFundsException;

public interface TransferService {

	void transfer(Long fromAccountId, Long toAccountId, Long amount) throws NotEnoughFundsException;
}
