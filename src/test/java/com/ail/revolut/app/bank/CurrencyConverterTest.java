package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CurrencyConverterTest {

	@Test
	public void testConvert() {
		CurrencyConverter converter = new CurrencyConverter();

		Money money = converter.convert(new Money(new BigDecimal("1"), Currency.USD), Currency.RUB);
		assertEquals(new Money(new BigDecimal("63.828"), Currency.RUB), money);

		money = converter.convert(new Money(new BigDecimal("1"), Currency.EUR), Currency.RUB);
		assertEquals(new Money(new BigDecimal("67.758"), Currency.RUB), money);

		money = converter.convert(new Money(new BigDecimal("1"), Currency.EUR), Currency.USD);
		assertEquals(new Money(new BigDecimal("1.062"), Currency.USD), money);

		money = converter.convert(new Money(new BigDecimal("3"), Currency.USD), Currency.EUR);
		assertEquals(new Money(new BigDecimal("2.826"), Currency.EUR), money);
	}
}