package com.ail.revolut.app.bank;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import org.apache.commons.lang3.Validate;

public class Bank {

	public Account createDepositAccount(Currency currency) {
		Validate.notNull(currency);
		return new DepositAccount(currency);
	}

	public void deposit(Account account, Money money) {
		account.deposit(money);
	}

	public void withdraw(Account account, Money money) throws NotEnoughFundsException {
		account.withdraw(money);
	}
}
