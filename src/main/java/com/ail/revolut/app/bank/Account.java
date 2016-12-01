package com.ail.revolut.app.bank;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public class Account {

	private final Currency currency;
	private Money balance;

	Account(Currency currency) {
		this.currency = currency;
		this.balance = new Money("0", currency);
	}

	public void deposit(Money money) {
		balance = balance.add(money.convertTo(currency));
	}

	public void withdraw(Money money) throws NotEnoughFundsException {
		if (balance.compareTo(money) < 0) {
			throw new NotEnoughFundsException("not enough funds");
		}
		balance = balance.subtract(money.convertTo(currency));
	}

	public Money getBalance() {
		return balance;
	}

	public Currency getCurrency() {
		return currency;
	}
}
