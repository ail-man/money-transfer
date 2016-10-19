package com.ail.revolut.app.dto;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class MoneyTest {

	@Test
	public void testToString() {
		Money money = new Money();

		assertThat(money.toString(), equalTo("Money(amount=null)"));

		money.setAmount(new BigDecimal("123"));

		assertThat(money.toString(), equalTo("Money(amount=123)"));
	}
}
