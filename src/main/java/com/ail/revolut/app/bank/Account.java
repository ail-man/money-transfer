package com.ail.revolut.app.bank;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Account {

	private final String id;
	private final Person owner;
	private Money balance;

	public Account(Account account) {
		this.id = account.getId();
		this.owner = account.getOwner();
		this.balance = account.getBalance();
	}

	private Account(String id, Person owner, Currency currency) {
		this.id = id;
		this.owner = owner;
		this.balance = new Money("0", currency);
	}

	public static Account create(String id, Person owner, Currency currency) {
		Validate.notNull(id);
		Validate.notNull(owner);
		Validate.notNull(currency);

		return new Account(id, owner, currency);
	}

	public String getId() {
		return id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Account account = (Account) o;

		return new EqualsBuilder()
				.append(id, account.id)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("owner", owner)
				.append("balance", balance)
				.toString();
	}
}
