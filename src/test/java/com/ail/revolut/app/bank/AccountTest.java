package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.Currency.EUR;
import static com.ail.revolut.app.bank.Currency.RUB;
import com.ail.revolut.app.exception.NotEnoughFundsException;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class AccountTest extends BaseTest {

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		Person person = new Person().withName("pers1");
		Account account = new Account(person, RUB);
		assertThat(account.getBalance(), equalTo(new Money("0", RUB)));
	}

	@Test
	public void testDeposit() throws Exception {
		Person person = new Person().withName("pers2");
		Account account = new Account(person, RUB);

		account.deposit(new Money("3.410", RUB));
		assertThat(account.getBalance(), equalTo(new Money("3.410", RUB)));

		account.deposit(new Money("2.350", RUB));
		assertThat(account.getBalance(), equalTo(new Money("5.760", RUB)));
	}

	@Test
	public void testWithdraw() throws Exception {
		Person person = new Person().withName("pers3");
		Account account = new Account(person, RUB);

		account.deposit(new Money("10.000", RUB));
		assertThat(account.getBalance(), equalTo(new Money("10.000", RUB)));

		account.withdraw(new Money("2.000", RUB));
		assertThat(account.getBalance(), equalTo(new Money("8.000", RUB)));

		account.withdraw(new Money("3.150", RUB));
		assertThat(account.getBalance(), equalTo(new Money("4.850", RUB)));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		Person person = new Person().withName("pers4");
		Account account = new Account(person, RUB);

		assertTestFails(() -> account.withdraw(new Money("1.000", RUB)), NotEnoughFundsException.class);

		account.deposit(new Money("10.000", RUB));
		assertTestFails(() -> account.withdraw(new Money("10.010", RUB)), NotEnoughFundsException.class);
	}

	@Test
	public void testDepositAmountDifferentCurrency() throws Exception {
		Person person = new Person().withName("pers5");
		Account account = new Account(person, RUB);

		account.deposit(new Money("3", EUR));
		assertThat(account.getBalance(), equalTo(new Money("203.2738853505", RUB)));
	}

	// TODO get commission to some bank account
	// TODO link to Account entity - ???

}
