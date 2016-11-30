package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;

public class Money {

	private final BigDecimal amount;
	private final Currency currency;

	public Money(BigDecimal amount, Currency currency) {
		Validate.isTrue(amount.signum() > 0, "Amount must be positive");
		Validate.notNull(currency);
		this.amount = amount;
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

}
