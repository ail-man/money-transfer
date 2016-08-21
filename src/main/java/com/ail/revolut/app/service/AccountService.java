package com.ail.revolut.app.service;

import com.ail.revolut.app.NotEnoughFundsException;
import com.ail.revolut.app.model.Account;

public interface AccountService {

	Account createAccount();

	Long getBalance(Long id);

	void deposit(Long id, Long amount);

	void withdraw(Long id, Long amount) throws NotEnoughFundsException;
}
