package com.ail.revolut.app.service;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface AccountService {

	Long createAccount();

	Long getBalance(Long id);

	void deposit(Long id, Long amount);

	void withdraw(Long id, Long amount) throws NotEnoughFundsException;
}
