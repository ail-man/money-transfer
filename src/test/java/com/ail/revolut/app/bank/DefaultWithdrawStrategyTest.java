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
		account = Account.create("", Person.create(""), USD);
		account.deposit(new Money("10", USD), new Money("0", USD));
		assertThat(account.getBalance(), equalTo(new Money("10", USD)));
		withdrawStrategy = new DefaultWithdrawStrategy();
	}

	@Test
	public void testWithdrawTheSameCurrency() throws Exception {
		Money commission;

		commission = withdrawStrategy.withdraw(account, new Money("4", USD));
		assertThat(account.getBalance(), equalTo(new Money("6", USD)));
		assertThat(commission, equalTo(new Money("0", USD)));

		commission = withdrawStrategy.withdraw(account, new Money("5", USD));
		assertThat(account.getBalance(), equalTo(new Money("1", USD)));
		assertThat(commission, equalTo(new Money("0", USD)));

		assertTestFails(() -> withdrawStrategy.withdraw(account, new Money("2", USD)), NotEnoughFundsException.class);
	}

	@Test
	public void testWithdrawTDifferentCurrency() throws Exception {
		Money commission;

		commission = withdrawStrategy.withdraw(account, new Money("3", EUR));
		assertThat(account.getBalance(), equalTo(new Money("6.8152866241", USD)));
		assertThat(commission, equalTo(new Money("0", USD)));

		commission = withdrawStrategy.withdraw(account, new Money("5", USD));
		assertThat(account.getBalance(), equalTo(new Money("1.8152866241", USD)));
		assertThat(commission, equalTo(new Money("0", USD)));

		assertTestFails(() -> withdrawStrategy.withdraw(account, new Money("2", EUR)), NotEnoughFundsException.class);
	}
}
