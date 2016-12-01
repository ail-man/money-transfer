package com.ail.revolut.app.bank;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface Account {

	void deposit(Money money);

	void withdraw(Money money) throws NotEnoughFundsException;

	BigDecimal getBalance();

	Currency getCurrency();
}
