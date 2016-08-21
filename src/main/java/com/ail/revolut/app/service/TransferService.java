package com.ail.revolut.app.service;

import com.ail.revolut.app.model.Account;

public interface TransferService {

	void transfer(Account from, Account to, long amount);
}
