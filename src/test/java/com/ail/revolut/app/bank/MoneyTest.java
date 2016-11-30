package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import static org.junit.Assert.fail;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoneyTest {

	private static final Logger logger = LoggerFactory.getLogger(MoneyTest.class);

	@Test
	public void testMoneyMustBePositiveOnly() {
		try {
			new Money(new BigDecimal("-10"), Currency.RUR);
			fail("should fail");
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
		}

		try {
			new Money(new BigDecimal("0"), Currency.RUR);
			fail("should fail");
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
		}
	}

	@Test
	public void testMoneyConstructor() {
		new Money(new BigDecimal("10"), Currency.RUR);
	}

	// TODO test equals
	// TODO test hashCode
	// TODO test toString
}
