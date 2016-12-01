package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Before;
import org.junit.Test;

public class BankAccountTest extends BaseTest {

	private BankAccount bankAccount;

	@Before
	public void init() {
		bankAccount = new BankAccount(Currency.RUR);
	}

	@Test
	public void testGetBalance() throws Exception {
		assertThat(bankAccount.getBalance(), is(notNullValue()));
	}

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		assertThat(bankAccount.getBalance(), equalTo(BigDecimal.ZERO));
	}

	@Test
	public void testDeposit() throws Exception {
		bankAccount.deposit(new Money(new BigDecimal("3.41"), Currency.RUR));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("3.41")));

		bankAccount.deposit(new Money(new BigDecimal("2.35"), Currency.RUR));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("5.76")));
	}

	@Test
	public void testWithDraw() throws Exception {
		bankAccount.deposit(new Money(new BigDecimal("10.00"), Currency.RUR));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("10.00")));

		bankAccount.withdraw(new Money(new BigDecimal("2.00"), Currency.RUR));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("8.00")));

		bankAccount.withdraw(new Money(new BigDecimal("3.15"), Currency.RUR));
		assertThat(bankAccount.getBalance(), equalTo(new BigDecimal("4.85")));
	}

	@Test
	public void testWithdrawAmountCantBeGreaterThanBalance() throws Exception {
		assertOperationFails(() -> bankAccount.withdraw(new Money(new BigDecimal("1.00"), Currency.RUR)), Exception.class, NotEnoughFundsException.class);

		bankAccount.deposit(new Money(new BigDecimal("10.00"), Currency.RUR));
		assertOperationFails(() -> bankAccount.withdraw(new Money(new BigDecimal("10.01"), Currency.RUR)), NotEnoughFundsException.class);
	}

	// TODO conversion if deposit/withdraw different currency
	// TODO link to Account entity - ???

}
