package com.ail.revolut.app.bank;

import java.util.HashMap;
import java.util.Map;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public class BankImpl implements Bank {

	private final Map<String, Account> accountMap;
	private int counter;

	private BankImpl() {
		accountMap = new HashMap<>();
		counter = 0;
	}

	static Bank create() {
		return new BankImpl();
	}

	public Account createAccount(Person person, Currency currency) {
		String accountId = String.valueOf(counter++);
		Account account = Account.create(accountId, person, currency);
		accountMap.put(accountId, account);
		return account;
	}

	@Override
	public Money getBalance(Account account) {
		return account.getBalance();
	}

	public Money deposit(Account account, Money amount, DepositStrategy depositStrategy) {
		return depositStrategy.deposit(account, amount);
	}

	@Override
	public Money withdraw(Account account, Money amount, WithdrawStrategy withdrawStrategy) throws NotEnoughFundsException {
		return withdrawStrategy.withdraw(account, amount);
	}

	@Override
	public Money transfer(Account fromAccount, Account toAccount, Money amount, TransferStrategy transferStrategy) throws NotEnoughFundsException {
		return transferStrategy.transfer(fromAccount, toAccount, amount);
	}

}
