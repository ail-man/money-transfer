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
	public void testMoneyConstructor() throws Exception {
		new Money("10", RUB);
	}

	@Test
	public void testEqualsReflexivity() throws Exception {
		Money money = new Money("12.99", RUB);

		assertThat(money, equalTo(money));
	}

	@Test
	public void testEqualsSymmetry() {
		Money money1 = new Money("1.23", EUR);
		Money money2 = new Money("1.23", EUR);

		assertThat(money1, equalTo(money2));
		assertThat(money2, equalTo(money1));
	}

	@Test
	public void testEqualsConsistency() {
		Money money1 = new Money("123.4", USD);
		Money money2 = new Money("123.4", USD);

		assertThat(money1, equalTo(money2));
		assertThat(money1, equalTo(money2));
	}

	@Test
	public void testEqualsTransitivity() {
		Money money1 = new Money("99.99", RUR);
		Money money2 = new Money("99.99", RUR);
		Money money3 = new Money("99.99", RUR);

		assertThat(money1, equalTo(money2));
		assertThat(money2, equalTo(money3));
		assertThat(money1, equalTo(money3));
	}

	@Test
	public void testNotEquals() throws Exception {
		Money money = new Money("12.99", RUB);

		assertThat(money, not(equalTo(new Money("4", RUB))));
		assertThat(money, not(equalTo(new Money("12.99", EUR))));
	}

	@Test
	public void testHashCodeConsistency() {
		Money money = new Money("12.99", RUB);

		assertThat(money.hashCode(), equalTo(money.hashCode()));
	}

	@Test
	public void testHashCodeEquals() {
		Money money1 = new Money("1", RUB);
		Money money2 = new Money("1", RUB);

		assertThat(money1, equalTo(money2));
		assertThat(money1.hashCode(), equalTo(money2.hashCode()));
	}

	@Test
	public void testToString() {
		logger.debug(new Money("12.99", RUB).toString());
	}

	@Test
	public void testConvertTo() throws Exception {
		assertThat(new Money("1", USD).convertTo(RUB), equalTo(new Money("63.828", RUB)));
		assertThat(new Money("1", EUR).convertTo(RUB), equalTo(new Money("67.7579617835", RUB)));
		assertThat(new Money("1", EUR).convertTo(USD), equalTo(new Money("1.0615711253", USD)));
		assertThat(new Money("3", USD).convertTo(EUR), equalTo(new Money("2.826", EUR)));
	}

	@Test
	public void testAdd() throws Exception {
		assertThat(new Money("2", USD).add(new Money("3", USD)), equalTo(new Money("5", USD)));
		assertThat(new Money("31", RUB).add(new Money("2", USD)), equalTo(new Money("158.656", RUB)));
	}

	@Test
	public void testSubtract() throws Exception {
		assertThat(new Money("7", USD).subtract(new Money("3", USD)), equalTo(new Money("4", USD)));
		assertThat(new Money("158.656", RUB).subtract(new Money("2", USD)), equalTo(new Money("31", RUB)));
	}

	@Test
	public void testMultiply() throws Exception {
		assertThat(new Money("2", USD).multiply("5"), equalTo(new Money("10", USD)));
		assertThat(new Money("7", USD).multiply("3.15"), equalTo(new Money("22.05", USD)));
	}

	@Test
	public void testDivide() throws Exception {
		assertThat(new Money("33", USD).divide("11"), equalTo(new Money("3", USD)));
		assertThat(new Money("99.15", USD).divide("3.25"), equalTo(new Money("30.5076923077", USD)));
	}

	@Test
	public void compareToTheSameCurrencies() throws Exception {
		assertThat(new Money("3", USD).compareTo(new Money("2", USD)), equalTo(1));
		assertThat(new Money("1", USD).compareTo(new Money("4", USD)), equalTo(-1));
		assertThat(new Money("5", USD).compareTo(new Money("5", USD)), equalTo(0));
	}

	@Test
	public void compareToDifferentCurrencies() throws Exception {
		assertTestFails(() -> new Money("1", USD).compareTo(new Money("63.828", RUB)), IllegalArgumentException.class);
	}

	@Test
	public void testSignum() throws Exception {
		assertThat(new Money("3", USD).signum(), equalTo(1));
		assertThat(new Money("-2", USD).signum(), equalTo(-1));
		assertThat(new Money("0", USD).signum(), equalTo(0));
	}

	@Test
	public void testMinTheSameCurrencies() throws Exception {
		assertThat(new Money("1", USD).min(new Money("1", USD)), equalTo(new Money("1", USD)));
		assertThat(new Money("3", USD).min(new Money("2", USD)), equalTo(new Money("2", USD)));
		assertThat(new Money("5", USD).min(new Money("7", USD)), equalTo(new Money("5", USD)));
	}

	@Test
	public void testMinDifferentCurrencies() throws Exception {
		assertTestFails(() -> new Money("3", USD).min(new Money("2", EUR)), IllegalArgumentException.class);
	}

	@Test
	public void testMaxTheSameCurrencies() throws Exception {
		assertThat(new Money("4", USD).max(new Money("4", USD)), equalTo(new Money("4", USD)));
		assertThat(new Money("1", USD).max(new Money("3", USD)), equalTo(new Money("3", USD)));
		assertThat(new Money("9", USD).max(new Money("6", USD)), equalTo(new Money("9", USD)));
	}

	@Test
	public void testMaxDifferentCurrencies() throws Exception {
		assertTestFails(() -> new Money("1", USD).min(new Money("1", EUR)), IllegalArgumentException.class);
	}

}
