package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class BankAccountTest extends BaseTest {

	@Test
	public void testNotNull() throws Exception {
		assertTestFails(() -> BankAccount.create(null), NullPointerException.class);
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		BankAccount bankAccount = BankAccount.create(Currency.RUB);
		assertThat(bankAccount.getBalance(), equalTo(BigDecimal.ZERO));
	}

	@Test
	public void testDeposit() throws Exception {
		BankAccount bankAccount = BankAccount.create(Currency.RUB);

		bankAccount.deposit(Money.create(new BigDecimal("3.41"), Currency.RUB));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("3.41")));

		bankAccount.deposit(Money.create(new BigDecimal("2.35"), Currency.RUB));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("5.76")));
	}

	@Test
	public void testDepositDiffrentCurrency() throws Exception {
		BankAccount bankAccount = BankAccount.create(Currency.RUB);
		assertTestFails(() -> bankAccount.deposit(Money.create(new BigDecimal("3.41"), Currency.EUR)), IllegalArgumentException.class);
	}

	@Test
	public void testWithdraw() throws Exception {
		BankAccount bankAccount = BankAccount.create(Currency.RUB);

		bankAccount.deposit(Money.create(new BigDecimal("10.00"), Currency.RUB));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("10.00")));

		bankAccount.withdraw(Money.create(new BigDecimal("2.00"), Currency.RUB));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("8.00")));

		bankAccount.withdraw(Money.create(new BigDecimal("3.15"), Currency.RUB));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("4.85")));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		BankAccount bankAccount = BankAccount.create(Currency.RUB);

		assertTestFails(() -> bankAccount.withdraw(Money.create(new BigDecimal("1.00"), Currency.RUB)), NotEnoughFundsException.class);

		bankAccount.deposit(Money.create(new BigDecimal("10.00"), Currency.RUB));
		assertTestFails(() -> bankAccount.withdraw(Money.create(new BigDecimal("10.01"), Currency.RUB)), NotEnoughFundsException.class);
	}

	// TODO conversion if deposit/withdraw different currency
	// TODO link to Account entity - ???

}
