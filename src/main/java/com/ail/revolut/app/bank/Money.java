package com.ail.revolut.app.bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Money {

	public static final int PRECISION = 10;

	private final BigDecimal amount;
	private final Currency currency;

	public Money(String amount, Currency currency) {
		this.amount = new BigDecimal(amount).setScale(PRECISION, BigDecimal.ROUND_CEILING);
		this.currency = currency;
	}

	public String getAmount() {
		return amount.toString();
	}

	public Currency getCurrency() {
		return currency;
	}

	public Money convertTo(Currency currency) {
		BigDecimal conversionRate = getConversionRate(currency.getRate(), this.currency.getRate());
		BigDecimal resultAmount = amount.multiply(conversionRate);
		return new Money(resultAmount.toString(), currency);
	}

	public Money add(Money money) {
		BigDecimal resultAmount;
		if (this.currency.equals(money.currency)) {
			resultAmount = this.amount.add(money.amount);
		} else {
			resultAmount = this.amount.add(money.convertTo(this.currency).amount);
		}
		return new Money(resultAmount.toString(), this.currency);
	}

	public Money subtract(Money money) {
		BigDecimal resultAmount;
		if (this.currency.equals(money.currency)) {
			resultAmount = this.amount.subtract(money.amount);
		} else {
			resultAmount = this.amount.subtract(money.convertTo(this.currency).amount);
		}
		return new Money(resultAmount.toString(), this.currency);
	}

	public Money multiply(String multiplicand) {
		return new Money(amount.multiply(new BigDecimal(multiplicand)).toString(), currency);
	}

	public Money divide(String divisor) {
		return new Money(amount.divide(new BigDecimal(divisor), PRECISION, BigDecimal.ROUND_CEILING).toString(), currency);
	}

	public int compareTo(Money money) {
		validateCurrencies(money);
		return this.amount.compareTo(money.amount);
	}

	private void validateCurrencies(Money money) {
		Validate.isTrue(this.currency.equals(money.currency), "Currencies must be the same");
	}

	private BigDecimal getConversionRate(BigDecimal rateFrom, BigDecimal rateTo) {
		return rateFrom.divide(rateTo, PRECISION, RoundingMode.CEILING);
	}

	public int signum() {
		return amount.signum();
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
		return amount + "(" + currency + ")";
	}

	public Money min(Money money) {
		validateCurrencies(money);
		return new Money(this.amount.min(money.amount).toString(), this.currency);
	}

	public Money max(Money money) {
		validateCurrencies(money);
		return new Money(this.amount.max(money.amount).toString(), this.currency);
	}
}
