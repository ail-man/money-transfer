package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Money money = (Money) o;

		return new EqualsBuilder()
				.append(amount, money.amount)
				.append(currency, money.currency)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(amount)
				.append(currency)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("amount", amount)
				.append("currency", currency)
				.toString();
	}
}
