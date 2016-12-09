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
		Person person = Person.create("1").withName("Vasili");
		Account account = bank.createAccount(person, RUB);

		DepositStrategy depositStrategy = (acc, money) -> {
			String commissionFactor;
			if (account.getCurrency().equals(money.getCurrency())) {
				commissionFactor = "0";
			} else {
				commissionFactor = "0.05";
			}
			Money commission = money.multiply(commissionFactor).convertTo(RUB);
			acc.deposit(money, commission);
			return commission;
		};

		Money deposit = new Money("2", RUB);
		Money commission = bank.deposit(account, deposit, depositStrategy);

		assertThat(commission, equalTo(new Money("0", RUB)));
		assertThat(account.getBalance(), equalTo(new Money("2", RUB)));

		deposit = new Money("10", USD);
		commission = bank.deposit(account, deposit, depositStrategy);

		assertThat(commission, equalTo(new Money("31.914", RUB)));
		assertThat(account.getBalance(), equalTo(new Money("608.366", RUB)));
	}

	// TODO Currency must load dynamically => All test presented with test Currency!!!
}
