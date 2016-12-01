package com.ail.revolut.app.bank;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public class Bank {

	public Account createAccount(Currency currency) {
		return new Account(currency);
	}

	public void deposit(Account account, Money money) {
		account.deposit(money);
	}

	public void withdraw(Account account, Money money) throws NotEnoughFundsException {
		account.withdraw(money);
	}
}
