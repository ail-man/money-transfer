package com.ail.revolut.app.service;

public interface TransferService {

	void transfer(Long fromAccountId, Long toAccountId, Long amount);
}
