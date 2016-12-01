package com.ail.revolut.app.bank;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import org.apache.commons.lang3.Validate;

public class Bank {

	public BankAccount createBankAccount(Currency currency) {
		Validate.notNull(currency);
		return new BankAccount(currency);
	}

	public void deposit(BankAccount bankAccount, Money money) {
		bankAccount.deposit(money);
	}

	public void withdraw(BankAccount bankAccount, Money money) throws NotEnoughFundsException {
		bankAccount.withdraw(money);
	}
}
