package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import com.ail.revolut.app.helper.BaseTest;
import org.junit.Test;

public class MoneyTest extends BaseTest {

	@Test
	public void testMoneyMustBePositiveOnly() throws Exception {
		assertTestFails(() -> new Money(new BigDecimal("0"), Currency.RUB), IllegalArgumentException.class);
		assertTestFails(() -> new Money(new BigDecimal("-10"), Currency.RUB), IllegalArgumentException.class);
	}

	@Test
	public void testMoneyConstructor() {
		new Money(new BigDecimal("10"), Currency.RUB);
	}

	// TODO test equals
	// TODO test hashCode
	// TODO test toString
}
