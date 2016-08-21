package com.ail.revolut.app.api;

import com.ail.revolut.app.NotEnoughFundsException;

public interface BankAccount {
	Long getId();

	Long getBalance();

	void deposit(long amount);

	void withdraw(long amount) throws NotEnoughFundsException;
}
