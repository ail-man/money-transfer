package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.Currency.RUB;
import static com.ail.revolut.app.bank.Currency.USD;
import com.ail.revolut.app.bank.deposit.DepositStrategy;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class BankTest extends BaseTest {

	@Test
	public void testCreateAccount() throws Exception {
		Bank bank = Bank.create();
		Person person = Person.create("");

		Account account = bank.createAccount(person, USD);
		assertThat(account.getOwner(), equalTo(person));
		assertThat(account.getCurrency(), equalTo(USD));
	}

	@Test
	public void testDeposit() throws Exception {
		Bank bank = Bank.create();
		Person person = Person.create("");
		Account account = bank.createAccount(person, RUB);

		DepositStrategy depositStrategy = (account1, money) -> {
			String commissionFactor = "0.01";
			Money commission = money.multiply(commissionFactor);
			//			account1.setBalance(account1.getBalance().add(money).subtract(commission));
			return commission;
		};

		Money deposit = new Money("2", RUB);
		Money commission = bank.deposit(account, deposit, depositStrategy);

		assertThat(commission, equalTo(new Money("0.02", RUB)));
		assertThat(account.getBalance(), equalTo(new Money("1.98", RUB)));

		assertThat(account.getBalance().add(commission), equalTo(deposit));
	}

	// TODO Currency must load dynamically => All test presented with test Currency
}
