package com.ail.revolut.app.bank;

import com.ail.revolut.app.bank.deposit.DepositStrategy;

public class Bank {

	public Account createAccount(Person person, Currency currency) {
		return new Account(person, currency);
	}

	public void deposit(Account account, Money money, DepositStrategy depositStrategy) {
		depositStrategy.deposit(account, money);
	}

	//	public void withdraw(Account account, Money money, WithdrawStrategy withdrawStrategy) throws NotEnoughFundsException {
	//		withdrawStrategy.withdraw(account, money);
	//	}
}
