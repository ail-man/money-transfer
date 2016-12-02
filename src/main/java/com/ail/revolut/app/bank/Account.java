package com.ail.revolut.app.bank;

import org.apache.commons.lang3.Validate;

public class Account {

	private final Person owner;
	private Money balance;

	public Account(Person owner, Currency currency) {
		Validate.notNull(owner);
		Validate.notNull(currency);
		this.owner = owner;
		this.balance = new Money("0", currency);
	}

	public Person getOwner() {
		return owner;
	}

	public Currency getCurrency() {
		return balance.getCurrency();
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		Validate.isTrue(getCurrency().equals(balance.getCurrency()), "currencies should be equals");
		this.balance = balance;
	}

}
