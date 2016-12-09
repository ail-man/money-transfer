package com.ail.revolut.app.bank;

import java.util.HashMap;
import java.util.Map;

import com.ail.revolut.app.bank.deposit.DepositStrategy;

public class Bank {

	private final Map<String, Account> accountMap;
	private int counter;

	private Bank() {
		accountMap = new HashMap<>();
		counter = 0;
	}

	public static Bank create() {
		return new Bank();
	}

	public Account createAccount(Person person, Currency currency) {
		String accountId = String.valueOf(counter++);
		Account account = Account.create(accountId, person, currency);
		accountMap.put(accountId, account);
		return account;
	}

	public Money deposit(Account account, Money amount, DepositStrategy depositStrategy) {
		return depositStrategy.deposit(account, amount);
	}

}
