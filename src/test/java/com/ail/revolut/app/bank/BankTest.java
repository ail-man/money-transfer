package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.Currency.RUB;
import static com.ail.revolut.app.bank.Currency.USD;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class BankTest extends BaseTest {

	@Test
	public void testCreateAccount() throws Exception {
		Bank bank = new Bank();
		Person person = new Person().withName("pers1");

		Account account = bank.createAccount(person, USD);
		assertThat(account.getOwner(), equalTo(person));
		assertThat(account.getBalance(), equalTo(new Money("0", USD)));
	}

	@Test
	public void testDepositTheSameCurrency() throws Exception {
		Bank bank = new Bank();
		Person person = new Person().withName("pers2");
		Account account = bank.createAccount(person, RUB);

		bank.deposit(account, new Money("2", RUB));
		assertThat(account.getBalance(), equalTo(new Money("2", RUB)));

		bank.deposit(account, new Money("3", RUB));
		assertThat(account.getBalance(), equalTo(new Money("5", RUB)));
	}

	@Test
	public void testDepositDifferentCurrency() throws Exception {
		Bank bank = new Bank();
		Person person = new Person().withName("pers3");
		Account account = bank.createAccount(person, RUB);

		bank.deposit(account, new Money("10", RUB));
		assertThat(account.getBalance(), equalTo(new Money("10", RUB)));

		bank.deposit(account, new Money("1", USD));
		assertThat(account.getBalance(), equalTo(new Money("73.828", RUB)));
	}

	// TODO can withdraw to negative balance (credit)
}
