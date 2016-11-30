package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public class BankAccount {

	private final Currency currency;
	private BigDecimal balance;

	public BankAccount(Currency currency) {
		this.currency = currency;
		this.balance = BigDecimal.ZERO;
	}

	public void deposit(Money money) {
		balance = balance.add(money.getAmount());
	}

	public void withdraw(Money money) throws NotEnoughFundsException {
		if (balance.compareTo(money.getAmount()) < 0) {
			throw new NotEnoughFundsException("not enough funds");
		}
		balance = balance.subtract(money.getAmount());
	}

	public BigDecimal getBalance() {
		return balance;
	}
}
