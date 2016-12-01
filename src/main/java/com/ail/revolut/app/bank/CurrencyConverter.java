package com.ail.revolut.app.bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {

	public Money convert(Money money, Currency toCurrency) {
		Currency moneyCurrency = money.getCurrency();
		BigDecimal moneyAmount = money.getAmount();

		BigDecimal conversionRate = getConversionRate(toCurrency.getRate(), moneyCurrency.getRate());

		BigDecimal resultAmount = moneyAmount.multiply(conversionRate);

		return new Money(resultAmount, toCurrency);
	}

	private BigDecimal getConversionRate(BigDecimal rateFrom, BigDecimal rateTo) {
		return rateFrom.divide(rateTo, 3, RoundingMode.CEILING);
	}
}
