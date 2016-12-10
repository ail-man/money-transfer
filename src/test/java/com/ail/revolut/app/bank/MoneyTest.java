package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.CurrencyImpl.EUR;
import static com.ail.revolut.app.bank.CurrencyImpl.RUB;
import static com.ail.revolut.app.bank.CurrencyImpl.RUR;
import static com.ail.revolut.app.bank.CurrencyImpl.USD;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import org.junit.Test;

public class MoneyTest extends BaseTest {

	@Test
	public void testEqualsReflexivity() throws Exception {
		Money money = Money.create("12.99", RUB);

		assertThat(money, equalTo(money));
	}

	@Test
	public void testEqualsSymmetry() {
		Money money1 = Money.create("1.23", EUR);
		Money money2 = Money.create("1.23", EUR);

		assertThat(money1, equalTo(money2));
		assertThat(money2, equalTo(money1));
	}

	@Test
	public void testEqualsConsistency() {
		Money money1 = Money.create("123.4", USD);
		Money money2 = Money.create("123.4", USD);

		assertThat(money1, equalTo(money2));
		assertThat(money1, equalTo(money2));
	}

	@Test
	public void testEqualsTransitivity() {
		Money money1 = Money.create("99.99", RUR);
		Money money2 = Money.create("99.99", RUR);
		Money money3 = Money.create("99.99", RUR);

		assertThat(money1, equalTo(money2));
		assertThat(money2, equalTo(money3));
		assertThat(money1, equalTo(money3));
	}

	@Test
	public void testNotEquals() throws Exception {
		Money money = Money.create("12.99", RUB);

		assertThat(money, not(equalTo(Money.create("4", RUB))));
		assertThat(money, not(equalTo(Money.create("12.99", EUR))));
	}

	@Test
	public void testHashCodeConsistency() {
		Money money = Money.create("12.99", RUB);

		assertThat(money.hashCode(), equalTo(money.hashCode()));
	}

	@Test
	public void testHashCodeEquals() {
		Money money1 = Money.create("1", RUB);
		Money money2 = Money.create("1", RUB);

		assertThat(money1, equalTo(money2));
		assertThat(money1.hashCode(), equalTo(money2.hashCode()));
	}

	@Test
	public void testToString() {
		logger.debug(Money.create("12.99", RUB).toString());
	}

	@Test
	public void testConvertTo() throws Exception {
		assertThat(Money.create("1", USD).convertTo(RUB), equalTo(Money.create("63.828", RUB)));
		assertThat(Money.create("1", EUR).convertTo(RUB), equalTo(Money.create("67.7579617835", RUB)));
		assertThat(Money.create("1", EUR).convertTo(USD), equalTo(Money.create("1.0615711253", USD)));
		assertThat(Money.create("3", USD).convertTo(EUR), equalTo(Money.create("2.826", EUR)));
	}

	@Test
	public void testAdd() throws Exception {
		assertThat(Money.create("2", USD).add(Money.create("3", USD)), equalTo(Money.create("5", USD)));
		assertThat(Money.create("31", RUB).add(Money.create("2", USD)), equalTo(Money.create("158.656", RUB)));
	}

	@Test
	public void testSubtract() throws Exception {
		assertThat(Money.create("7", USD).subtract(Money.create("3", USD)), equalTo(Money.create("4", USD)));
		assertThat(Money.create("158.656", RUB).subtract(Money.create("2", USD)), equalTo(Money.create("31", RUB)));
	}

	@Test
	public void testMultiply() throws Exception {
		assertThat(Money.create("2", USD).multiply("5"), equalTo(Money.create("10", USD)));
		assertThat(Money.create("7", USD).multiply("3.15"), equalTo(Money.create("22.05", USD)));
	}

	@Test
	public void testDivide() throws Exception {
		assertThat(Money.create("33", USD).divide("11"), equalTo(Money.create("3", USD)));
		assertThat(Money.create("99.15", USD).divide("3.25"), equalTo(Money.create("30.5076923077", USD)));
	}

	@Test
	public void compareToTheSameCurrencies() throws Exception {
		assertThat(Money.create("3", USD).compareTo(Money.create("2", USD)), equalTo(1));
		assertThat(Money.create("1", USD).compareTo(Money.create("4", USD)), equalTo(-1));
		assertThat(Money.create("5", USD).compareTo(Money.create("5", USD)), equalTo(0));
	}

	@Test
	public void compareToDifferentCurrencies() throws Exception {
		assertTestFails(() -> Money.create("1", USD).compareTo(Money.create("63.828", RUB)), IllegalArgumentException.class);
	}

	@Test
	public void testSignum() throws Exception {
		assertThat(Money.create("3", USD).signum(), equalTo(1));
		assertThat(Money.create("-2", USD).signum(), equalTo(-1));
		assertThat(Money.zero(USD).signum(), equalTo(0));
	}

	@Test
	public void testMinTheSameCurrencies() throws Exception {
		assertThat(Money.create("1", USD).min(Money.create("1", USD)), equalTo(Money.create("1", USD)));
		assertThat(Money.create("3", USD).min(Money.create("2", USD)), equalTo(Money.create("2", USD)));
		assertThat(Money.create("5", USD).min(Money.create("7", USD)), equalTo(Money.create("5", USD)));
	}

	@Test
	public void testMinDifferentCurrencies() throws Exception {
		assertTestFails(() -> Money.create("3", USD).min(Money.create("2", EUR)), IllegalArgumentException.class);
	}

	@Test
	public void testMaxTheSameCurrencies() throws Exception {
		assertThat(Money.create("4", USD).max(Money.create("4", USD)), equalTo(Money.create("4", USD)));
		assertThat(Money.create("1", USD).max(Money.create("3", USD)), equalTo(Money.create("3", USD)));
		assertThat(Money.create("9", USD).max(Money.create("6", USD)), equalTo(Money.create("9", USD)));
	}

	@Test
	public void testMaxDifferentCurrencies() throws Exception {
		assertTestFails(() -> Money.create("1", USD).min(Money.create("1", EUR)), IllegalArgumentException.class);
	}

}
