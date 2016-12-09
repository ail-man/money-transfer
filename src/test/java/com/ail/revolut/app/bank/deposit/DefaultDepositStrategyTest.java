package com.ail.revolut.app.bank.deposit;

import com.ail.revolut.app.bank.Account;
import static com.ail.revolut.app.bank.Currency.RUB;
import static com.ail.revolut.app.bank.Currency.USD;
import com.ail.revolut.app.bank.Money;
import com.ail.revolut.app.bank.Person;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Before;
import org.junit.Test;

public class DefaultDepositStrategyTest extends BaseTest {

	private Account account;
	private DepositStrategy depositStrategy;

	@Before
	public void init() throws Exception {
		account = new Account(new Person().withName("pers"), RUB);
		depositStrategy = new DefaultDepositStrategy();
	}

	@Test
	public void testDepositTheSameCurrency() throws Exception {
		Money commission;

		commission = depositStrategy.deposit(account, new Money("2", RUB));
		assertThat(account.getBalance(), equalTo(new Money("2", RUB)));
		assertThat(commission, equalTo(new Money("0", RUB)));

		commission = depositStrategy.deposit(account, new Money("3", RUB));
		assertThat(account.getBalance(), equalTo(new Money("5", RUB)));
		assertThat(commission, equalTo(new Money("0", RUB)));
	}

	@Test
	public void testDepositDifferentCurrency() throws Exception {
		Money commission;

		commission = depositStrategy.deposit(account, new Money("10", RUB));
		assertThat(account.getBalance(), equalTo(new Money("10", RUB)));
		assertThat(commission, equalTo(new Money("0", RUB)));

		commission = depositStrategy.deposit(account, new Money("2", USD));
		assertThat(account.getBalance(), equalTo(new Money("137.656", RUB)));
		assertThat(commission, equalTo(new Money("0", RUB)));
	}

}
