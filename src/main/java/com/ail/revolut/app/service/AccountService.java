package com.ail.revolut.app.service;

import com.ail.revolut.app.NotEnoughFundsException;
import com.ail.revolut.app.model.Account;

// TODO develop AccountService, that will have business logic from Account class and this createAccount() method
public interface AccountService {

	Account createAccount();

	Long getBalance(Long id);

	void deposit(Long id, Long amount);

	void withdraw(Long id, Long amount) throws NotEnoughFundsException;
}
