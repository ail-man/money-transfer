package com.ail.revolut.app.bank;

import java.math.BigDecimal;

public class CurrencyConverter {

	public Money convert(Money money, Currency toCurrency) {
		Currency moneyCurrency = money.getCurrency();
		BigDecimal amount = money.getAmount();

		BigDecimal conversionRate = getConversionRate(toCurrency.getRate(), moneyCurrency.getRate());

		BigDecimal result = amount.multiply(conversionRate);

		return new Money(result, toCurrency);
	}

	private BigDecimal getConversionRate(BigDecimal rateFrom, BigDecimal rateTo) {
		return rateFrom.divide(rateFrom).multiply(rateTo);
	}
}
