package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.CurrencyImpl.EUR;
import static com.ail.revolut.app.bank.CurrencyImpl.RUB;
import static com.ail.revolut.app.bank.CurrencyImpl.USD;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class AccountTest extends BaseTest {

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		Account account = Account.create("", Person.create(), RUB);
		assertThat(account.getBalance(), equalTo(Money.zero(RUB)));
	}

	@Test
	public void testDepositTheSameCurrency() throws Exception {
		Account account = Account.create("", Person.create(), RUB);

		account.deposit(Money.create("10", RUB), Money.zero(RUB));
		assertThat(account.getBalance(), equalTo(Money.create("10", RUB)));

		account.deposit(Money.create("20", RUB), Money.create("3", RUB));
		assertThat(account.getBalance(), equalTo(Money.create("27", RUB)));

		account.deposit(Money.create("30", RUB), Money.create("5", RUB));
		assertThat(account.getBalance(), equalTo(Money.create("52", RUB)));
	}

	@Test
	public void testDepositDiferentCurrency() throws Exception {
		Account account = Account.create("1", Person.create(), USD);

		account.deposit(Money.create("100", RUB), Money.zero(USD));
		assertThat(account.getBalance(), equalTo(Money.create("1.5667105400", USD)));

		account.deposit(Money.create("20", EUR), Money.create("3", RUB));
		assertThat(account.getBalance(), equalTo(Money.create("22.7511317298", USD)));

		account.deposit(Money.create("30", USD), Money.create("5", EUR));
		assertThat(account.getBalance(), equalTo(Money.create("47.4432761033", USD)));
	}

	@Test
	public void testWithdrawTheSameCurrency() throws Exception {
		Account account = Account.create("", Person.create(), RUB);

		account.withdraw(Money.create("10", RUB), Money.zero(RUB));
		assertThat(account.getBalance(), equalTo(Money.create("-10", RUB)));

		account.withdraw(Money.create("20", RUB), Money.create("3", RUB));
		assertThat(account.getBalance(), equalTo(Money.create("-33", RUB)));

		account.withdraw(Money.create("30", RUB), Money.create("5", RUB));
		assertThat(account.getBalance(), equalTo(Money.create("-68", RUB)));
	}

	@Test
	public void testtWithdrawDiferentCurrency() throws Exception {
		Account account = Account.create("1", Person.create(), USD);

		account.withdraw(Money.create("100", RUB), Money.zero(USD));
		assertThat(account.getBalance(), equalTo(Money.create("-1.5667105400", USD)));

		account.withdraw(Money.create("20", EUR), Money.create("3", RUB));
		assertThat(account.getBalance(), equalTo(Money.create("-22.8451343622", USD)));

		account.withdraw(Money.create("30", USD), Money.create("5", EUR));
		assertThat(account.getBalance(), equalTo(Money.create("-58.1529899887", USD)));
	}

}
