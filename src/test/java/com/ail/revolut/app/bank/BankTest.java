package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.Currency.RUB;
import static com.ail.revolut.app.bank.Currency.USD;
import com.ail.revolut.app.bank.deposit.DepositStrategy;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BankTest extends BaseTest {

	@Test
	public void testCreateAccount() throws Exception {
		Bank bank = new Bank();
		Person person = new Person().withName("pers1");

		Account account = bank.createAccount(person, USD);
		assertThat(account.getOwner(), equalTo(person));
		assertThat(account.getCurrency(), equalTo(USD));
		// TODO fix duplication
		assertThat(account.getBalance().getCurrency(), equalTo(USD));
	}

	@Test
	public void testDeposit() throws Exception {
		Bank bank = new Bank();
		Person person = new Person().withName("pers2");
		Account account = bank.createAccount(person, RUB);
		DepositStrategy depositStrategy = mock(DepositStrategy.class);

		when(depositStrategy.deposit(account, new Money("2", RUB))).thenReturn(new Money("0", RUB));
	}

	// TODO strategy can withdraw to negative balance (credit)
	// TODO strategy get commission to some bank account
	// TODO strategy link to Account entity - ???
}
