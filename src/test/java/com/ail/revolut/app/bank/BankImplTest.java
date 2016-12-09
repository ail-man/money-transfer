package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.CurrencyImpl.EUR;
import static com.ail.revolut.app.bank.CurrencyImpl.RUB;
import static com.ail.revolut.app.bank.CurrencyImpl.USD;
import com.ail.revolut.app.bank.deposit.DepositStrategy;
import com.ail.revolut.app.bank.withdraw.WithdrawStrategy;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class BankImplTest extends BaseTest {

	@Test
	public void testCreateAccount() throws Exception {
		BankImpl bankImpl = BankImpl.create();
		Person person = Person.create("");

		Account account = bankImpl.createAccount(person, USD);
		assertThat(account.getOwner(), equalTo(person));
		assertThat(account.getCurrency(), equalTo(USD));
	}

	@Test
	public void testDeposit() throws Exception {
		BankImpl bankImpl = BankImpl.create();
		Person person = Person.create("1").withName("Vasili");
		Account account = bankImpl.createAccount(person, RUB);

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

		Money amount = new Money("2", RUB);
		Money commission = bankImpl.deposit(account, amount, depositStrategy);

		assertThat(commission, equalTo(new Money("0", RUB)));
		assertThat(account.getBalance(), equalTo(new Money("2", RUB)));

		amount = new Money("10", USD);
		commission = bankImpl.deposit(account, amount, depositStrategy);

		assertThat(commission, equalTo(new Money("31.914", RUB)));
		assertThat(account.getBalance(), equalTo(new Money("608.366", RUB)));
	}

	@Test
	public void testWithdraw() throws Exception {
		BankImpl bankImpl = BankImpl.create();
		Person person = Person.create("2").withName("Petr");
		Account account = bankImpl.createAccount(person, USD);

		WithdrawStrategy withdrawStrategy = (acc, money) -> {
			String commissionFactor;
			if (account.getCurrency().equals(money.getCurrency())) {
				commissionFactor = "0";
			} else {
				commissionFactor = "0.05";
			}
			Money commission = money.multiply(commissionFactor).convertTo(USD);
			acc.withdraw(money, commission);
			return commission;
		};

		Money amount = new Money("2", EUR);
		Money commission = bankImpl.withdraw(account, amount, withdrawStrategy);

		assertThat(commission, equalTo(new Money("0.1061571126", USD)));
		assertThat(account.getBalance(), equalTo(new Money("-2.2292993632", USD)));

		amount = new Money("10", USD);
		commission = bankImpl.withdraw(account, amount, withdrawStrategy);

		assertThat(commission, equalTo(new Money("0", USD)));
		assertThat(account.getBalance(), equalTo(new Money("-12.2292993632", USD)));
	}
}