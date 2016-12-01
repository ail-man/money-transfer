package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class DepositAccountTest extends BaseTest {

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		Account account = new DepositAccount(Currency.RUB);
		assertThat(account.getBalance(), equalTo(BigDecimal.ZERO));
	}

	@Test
	public void testDeposit() throws Exception {
		Account account = new DepositAccount(Currency.RUB);

		account.deposit(new Money(new BigDecimal("3.41"), Currency.RUB));
		assertThat(account.getBalance(), equalTo(new BigDecimal("3.41")));

		account.deposit(new Money(new BigDecimal("2.35"), Currency.RUB));
		assertThat(account.getBalance(), equalTo(new BigDecimal("5.76")));
	}

	@Test
	public void testDepositDiffrentCurrency() throws Exception {
		Account account = new DepositAccount(Currency.RUB);
		assertTestFails(() -> account.deposit(new Money(new BigDecimal("3.41"), Currency.EUR)), IllegalArgumentException.class);
	}

	@Test
	public void testWithdraw() throws Exception {
		Account account = new DepositAccount(Currency.RUB);

		account.deposit(new Money(new BigDecimal("10.00"), Currency.RUB));
		assertThat(account.getBalance(), equalTo(new BigDecimal("10.00")));

		account.withdraw(new Money(new BigDecimal("2.00"), Currency.RUB));
		assertThat(account.getBalance(), equalTo(new BigDecimal("8.00")));

		account.withdraw(new Money(new BigDecimal("3.15"), Currency.RUB));
		assertThat(account.getBalance(), equalTo(new BigDecimal("4.85")));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		Account account = new DepositAccount(Currency.RUB);

		assertTestFails(() -> account.withdraw(new Money(new BigDecimal("1.00"), Currency.RUB)), NotEnoughFundsException.class);

		account.deposit(new Money(new BigDecimal("10.00"), Currency.RUB));
		assertTestFails(() -> account.withdraw(new Money(new BigDecimal("10.01"), Currency.RUB)), NotEnoughFundsException.class);
	}

	// TODO conversion if deposit/withdraw different currency
	// TODO link to Account entity - ???

}
