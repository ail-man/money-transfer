package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.CurrencyImpl.EUR;
import static com.ail.revolut.app.bank.CurrencyImpl.USD;
import com.ail.revolut.app.exception.NotEnoughFundsException;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Before;
import org.junit.Test;

public class DefaultWithdrawStrategyTest extends BaseTest {

	private Account account;
	private WithdrawStrategy withdrawStrategy;

	@Before
	public void init() throws Exception {
		account = Account.create("", Person.create(), USD);
		account.deposit(Money.create("10", USD), Money.zero(USD));
		assertThat(account.getBalance(), equalTo(Money.create("10", USD)));
		withdrawStrategy = new DefaultWithdrawStrategy();
	}

	@Test
	public void testWithdrawTheSameCurrency() throws Exception {
		Money commission;

		commission = withdrawStrategy.withdraw(account, Money.create("4", USD));
		assertThat(account.getBalance(), equalTo(Money.create("6", USD)));
		assertThat(commission, equalTo(Money.zero(USD)));

		commission = withdrawStrategy.withdraw(account, Money.create("5", USD));
		assertThat(account.getBalance(), equalTo(Money.create("1", USD)));
		assertThat(commission, equalTo(Money.zero(USD)));

		assertTestFails(() -> withdrawStrategy.withdraw(account, Money.create("2", USD)), NotEnoughFundsException.class);
	}

	@Test
	public void testWithdrawTDifferentCurrency() throws Exception {
		Money commission;

		commission = withdrawStrategy.withdraw(account, Money.create("3", EUR));
		assertThat(account.getBalance(), equalTo(Money.create("6.8152866241", USD)));
		assertThat(commission, equalTo(Money.zero(USD)));

		commission = withdrawStrategy.withdraw(account, Money.create("5", USD));
		assertThat(account.getBalance(), equalTo(Money.create("1.8152866241", USD)));
		assertThat(commission, equalTo(Money.zero(USD)));

		assertTestFails(() -> withdrawStrategy.withdraw(account, Money.create("2", EUR)), NotEnoughFundsException.class);
	}
}
