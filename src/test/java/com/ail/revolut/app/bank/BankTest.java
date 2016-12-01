package com.ail.revolut.app.bank;

import java.math.BigDecimal;

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
		Account account = bank.createDepositAccount(Currency.RUB);
		assertThat(account.getBalance(), equalTo(BigDecimal.ZERO));
	}

	@Test
	public void testDeposit() throws Exception {
		Account account = bank.createDepositAccount(Currency.RUB);

		bank.deposit(account, new Money(new BigDecimal("2"), Currency.RUB));
		assertThat(account.getBalance(), equalTo(new BigDecimal("2")));

		bank.deposit(account, new Money(new BigDecimal("3"), Currency.RUB));
		assertThat(account.getBalance(), equalTo(new BigDecimal("5")));
	}

	// TODO can withdraw to negative balance (credit)
}
