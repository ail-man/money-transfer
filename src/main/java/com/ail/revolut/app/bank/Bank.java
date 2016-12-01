package com.ail.revolut.app.bank;

public class Bank {

	public BankAccount createBankAccount(Currency currency) {
		return BankAccount.create(currency);
	}

	public void deposit(BankAccount bankAccount, Money money) {
		bankAccount.deposit(money);
	}
}
