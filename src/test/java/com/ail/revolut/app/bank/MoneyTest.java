package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import org.junit.Test;

public class MoneyTest extends BaseTest {

	@Test
	public void testMoneyMustBePositiveOnly() throws Exception {
		assertTestFails(() -> Money.create(new BigDecimal("0"), Currency.RUB), IllegalArgumentException.class);
		assertTestFails(() -> Money.create(new BigDecimal("-10"), Currency.RUB), IllegalArgumentException.class);
	}

	@Test
	public void testMoneyConstructor() throws Exception {
		Money.create(new BigDecimal("10"), Currency.RUB);
	}

	@Test
	public void testEqualsReflexivity() throws Exception {
		Money money = Money.create(new BigDecimal("12.99"), Currency.RUB);

		assertThat(money, equalTo(money));
	}

	@Test
	public void testEqualsSymmetry() {
		Money money1 = Money.create(new BigDecimal("1.23"), Currency.EUR);
		Money money2 = Money.create(new BigDecimal("1.23"), Currency.EUR);

		assertThat(money1, equalTo(money2));
		assertThat(money2, equalTo(money1));
	}

	@Test
	public void testEqualsConsistency() {
		Money money1 = Money.create(new BigDecimal("123.4"), Currency.USD);
		Money money2 = Money.create(new BigDecimal("123.4"), Currency.USD);

		assertThat(money1, equalTo(money2));
		assertThat(money1, equalTo(money2));
	}

	@Test
	public void testEqualsTransitivity() {
		Money money1 = Money.create(new BigDecimal("99.99"), Currency.RUR);
		Money money2 = Money.create(new BigDecimal("99.99"), Currency.RUR);
		Money money3 = Money.create(new BigDecimal("99.99"), Currency.RUR);

		assertThat(money1, equalTo(money2));
		assertThat(money2, equalTo(money3));
		assertThat(money1, equalTo(money3));
	}


	@Test
	public void testNotEquals() throws Exception {
		Money money = Money.create(new BigDecimal("12.99"), Currency.RUB);

		assertThat(money, not(equalTo(Money.create(new BigDecimal("4"), Currency.RUB))));
		assertThat(money, not(equalTo(Money.create(new BigDecimal("12.99"), Currency.EUR))));
	}

	@Test
	public void testHashCodeConsistency() {
		Money money = Money.create(new BigDecimal("12.99"), Currency.RUB);

		assertThat(money.hashCode(), equalTo(money.hashCode()));
	}

	@Test
	public void testHashCodeEquals() {
		Money money1 = Money.create(new BigDecimal("1"), Currency.RUB);
		Money money2 = Money.create(new BigDecimal("1"), Currency.RUB);
		assertThat(money1, equalTo(money2));
		assertThat(money1.hashCode(), equalTo(money2.hashCode()));
	}

	@Test
	public void testToString() {
		logger.debug(Money.create(new BigDecimal("12.99"), Currency.RUB).toString());
	}

}
