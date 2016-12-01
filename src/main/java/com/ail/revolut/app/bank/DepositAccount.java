package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;
import org.apache.commons.lang3.Validate;

public class DepositAccount implements Account {

	private final Currency currency;
	private BigDecimal balance;

	DepositAccount(Currency currency) {
		this.currency = currency;
		this.balance = BigDecimal.ZERO;
	}

	public void deposit(Money money) {
		Validate.notNull(money);
		if (!money.getCurrency().equals(currency)) {
			throw new IllegalArgumentException("currency is different");
		}
		balance = balance.add(money.getAmount());
	}

	public void withdraw(Money money) throws NotEnoughFundsException {
		if (balance.compareTo(money.getAmount()) < 0) {
			throw new NotEnoughFundsException("not enough funds");
		}
		balance = balance.subtract(money.getAmount());
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public Currency getCurrency() {
		return currency;
	}
}
