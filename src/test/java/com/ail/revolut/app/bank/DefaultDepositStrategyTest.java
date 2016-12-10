package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.CurrencyImpl.RUB;
import static com.ail.revolut.app.bank.CurrencyImpl.USD;
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
		account = Account.create("", Person.create(), RUB);
		depositStrategy = new DefaultDepositStrategy();
	}

	@Test
	public void testDepositTheSameCurrency() throws Exception {
		Money commission;

		commission = depositStrategy.deposit(account, Money.create("2", RUB));
		assertThat(account.getBalance(), equalTo(Money.create("2", RUB)));
		assertThat(commission, equalTo(Money.zero(RUB)));

		commission = depositStrategy.deposit(account, Money.create("3", RUB));
		assertThat(account.getBalance(), equalTo(Money.create("5", RUB)));
		assertThat(commission, equalTo(Money.zero(RUB)));
	}

	@Test
	public void testDepositDifferentCurrency() throws Exception {
		Money commission;

		commission = depositStrategy.deposit(account, Money.create("10", RUB));
		assertThat(account.getBalance(), equalTo(Money.create("10", RUB)));
		assertThat(commission, equalTo(Money.zero(RUB)));

		commission = depositStrategy.deposit(account, Money.create("2", USD));
		assertThat(account.getBalance(), equalTo(Money.create("137.656", RUB)));
		assertThat(commission, equalTo(Money.zero(RUB)));
	}

}
