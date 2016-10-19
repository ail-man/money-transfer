package com.ail.revolut.app.logic;

import java.math.BigInteger;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface AccountManager {

	Long createAccount();

	BigInteger getBalance(Long id);

	void deposit(Long id, BigInteger amount);

	void withdraw(Long id, BigInteger amount) throws NotEnoughFundsException;
}
