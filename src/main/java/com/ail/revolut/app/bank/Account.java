package com.ail.revolut.app.bank;

import org.apache.commons.lang3.Validate;

public class Account {

	private final Person owner;
	private final Currency currency;
	private Money balance;

	public Account(Person owner, Currency currency) {
		Validate.notNull(owner);
		Validate.notNull(currency);
		this.owner = owner;
		this.currency = currency;
		this.balance = new Money("0", currency);
	}

	public Person getOwner() {
		return owner;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		Validate.notNull(balance);
		this.balance = balance;
	}

}
