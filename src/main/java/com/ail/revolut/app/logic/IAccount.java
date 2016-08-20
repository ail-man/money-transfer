package com.ail.revolut.app.logic;

import com.ail.revolut.app.NotEnoughFundsException;

public interface IAccount {

	Long getId();

	long getBallance();

	void deposit(long amount);

	void withdraw(long amount) throws NotEnoughFundsException;


}
