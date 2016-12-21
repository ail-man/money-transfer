package com.ail.revolut.app.bank.integration;

import com.ail.revolut.app.bank.Account;
import com.ail.revolut.app.bank.Bank;
import static com.ail.revolut.app.bank.CurrencyImpl.USD;
import com.ail.revolut.app.bank.DefaultDepositStrategy;
import com.ail.revolut.app.bank.Money;
import com.ail.revolut.app.bank.Person;
import com.ail.revolut.app.bank.Platform;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class TestBank extends BaseTest {

	@Test
	public void testFunction() throws Exception {
		Person person = Person.create();
		Bank bank = Platform.createBank();
		Account account = bank.createAccount(person, USD);

		Money money = bank.getBalance(account);
		assertThat(money, equalTo(Money.zero(USD)));

		bank.deposit(account, Money.create("100", USD), new DefaultDepositStrategy());
	}

	// TODO refactor with https://dzone.com/articles/osgi-gateway-micro-services
}
