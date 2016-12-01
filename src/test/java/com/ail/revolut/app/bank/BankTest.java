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
		BankAccount bankAccount = bank.createBankAccount(Currency.RUB);
		assertThat(bankAccount.getBalance(), equalTo(BigDecimal.ZERO));
	}

	@Test
	public void testDeposit() throws Exception {
		BankAccount bankAccount = bank.createBankAccount(Currency.RUB);

		bank.deposit(bankAccount, new Money(new BigDecimal("2"), Currency.RUB));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("2")));

		bank.deposit(bankAccount, new Money(new BigDecimal("3"), Currency.RUB));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("5")));
	}
}
