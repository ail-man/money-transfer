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
		Account account = Account.create("", Person.create(""), RUB);
		assertThat(account.getBalance(), equalTo(new Money("0", RUB)));
	}

	@Test
	public void testDepositTheSameCurrency() throws Exception {
		Account account = Account.create("", Person.create(""), RUB);

		account.deposit(new Money("10", RUB), new Money("0", RUB));
		assertThat(account.getBalance(), equalTo(new Money("10", RUB)));

		account.deposit(new Money("20", RUB), new Money("3", RUB));
		assertThat(account.getBalance(), equalTo(new Money("27", RUB)));

		account.deposit(new Money("30", RUB), new Money("5", RUB));
		assertThat(account.getBalance(), equalTo(new Money("52", RUB)));
	}

	@Test
	public void testDepositDiferentCurrency() throws Exception {
		Account account = Account.create("1", Person.create("1"), USD);

		account.deposit(new Money("100", RUB), new Money("0", USD));
		assertThat(account.getBalance(), equalTo(new Money("1.5667105400", USD)));

		account.deposit(new Money("20", EUR), new Money("3", RUB));
		assertThat(account.getBalance(), equalTo(new Money("22.7511317298", USD)));

		account.deposit(new Money("30", USD), new Money("5", EUR));
		assertThat(account.getBalance(), equalTo(new Money("47.4432761033", USD)));
	}

	@Test
	public void testWithdrawTheSameCurrency() throws Exception {
		Account account = Account.create("", Person.create(""), RUB);

		account.withdraw(new Money("10", RUB), new Money("0", RUB));
		assertThat(account.getBalance(), equalTo(new Money("-10", RUB)));

		account.withdraw(new Money("20", RUB), new Money("3", RUB));
		assertThat(account.getBalance(), equalTo(new Money("-33", RUB)));

		account.withdraw(new Money("30", RUB), new Money("5", RUB));
		assertThat(account.getBalance(), equalTo(new Money("-68", RUB)));
	}

	@Test
	public void testtWithdrawDiferentCurrency() throws Exception {
		Account account = Account.create("1", Person.create("1"), USD);

		account.withdraw(new Money("100", RUB), new Money("0", USD));
		assertThat(account.getBalance(), equalTo(new Money("-1.5667105400", USD)));

		account.withdraw(new Money("20", EUR), new Money("3", RUB));
		assertThat(account.getBalance(), equalTo(new Money("-22.8451343622", USD)));

		account.withdraw(new Money("30", USD), new Money("5", EUR));
		assertThat(account.getBalance(), equalTo(new Money("-58.1529899887", USD)));
	}

}
