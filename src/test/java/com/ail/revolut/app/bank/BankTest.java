package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.Currency.RUB;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Before;
import org.junit.Test;

public class BankTest extends BaseTest {

	private Bank bank;

	@Before
	public void init() {
		bank = new Bank();
	}

	@Test
	public void testCreateBankAccount() throws Exception {
		Account account = bank.createDepositAccount(RUB);
		assertThat(account.getBalance(), equalTo(new Money("0", RUB)));
	}

	@Test
	public void testDepositTheSameCurrency() throws Exception {
		Account account = bank.createDepositAccount(RUB);

		bank.deposit(account, new Money("2", RUB));
		assertThat(account.getBalance(), equalTo(new Money("2", RUB)));

		bank.deposit(account, new Money("3", RUB));
		assertThat(account.getBalance(), equalTo(new Money("5", RUB)));
	}

	@Test
	public void testDepositDifferentCurrency() throws Exception {
		Account account = bank.createDepositAccount(RUB);

		bank.deposit(account, new Money("10", RUB));
		assertThat(account.getBalance(), equalTo(new Money("10", RUB)));

		bank.deposit(account, new Money("1", Currency.USD));
		assertThat(account.getBalance(), equalTo(new Money("73.828", RUB)));
	}

	// TODO can withdraw to negative balance (credit)
}
