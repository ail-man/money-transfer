package com.ail.revolut.app.logic;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface AccountManager {

	Long createAccount();

	Long getBalance(Long id);

	void deposit(Long id, Long amount);

	void withdraw(Long id, Long amount) throws NotEnoughFundsException;
}
