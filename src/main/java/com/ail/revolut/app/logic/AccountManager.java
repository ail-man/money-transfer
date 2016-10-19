package com.ail.revolut.app.logic;

import java.math.BigDecimal;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface AccountManager {

	Long createAccount();

	BigDecimal getBalance(Long id);

	void deposit(Long id, BigDecimal amount);

	void withdraw(Long id, BigDecimal amount) throws NotEnoughFundsException;
}
