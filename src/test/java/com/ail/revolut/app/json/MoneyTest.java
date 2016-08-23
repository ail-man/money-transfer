package com.ail.revolut.app.json;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MoneyTest {

	@Test
	public void testToString() {
		Money money = new Money();

		assertThat(money.toString(), equalTo("Money(amount=null)"));

		money.setAmount(123L);

		assertThat(money.toString(), equalTo("Money(amount=123)"));
	}
}
