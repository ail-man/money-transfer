package com.ail.revolut.app.bank;

import java.util.HashMap;
import java.util.Map;

import com.ail.revolut.app.bank.deposit.DepositStrategy;
import org.apache.commons.lang3.Validate;

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

	public Money deposit(Account account, Money money, DepositStrategy depositStrategy) {
		validateDeposit(account, money, depositStrategy);

		return depositStrategy.deposit(account, money);
	}

	private void validateDeposit(Account account, Money money, DepositStrategy depositStrategy) {
		Account validationAccount = new Account(account);

		Money balanceBefore = validationAccount.getBalance();
		Money commission = depositStrategy.deposit(validationAccount, money);
		Money balanceAfter = validationAccount.getBalance();
		Money depositedAmount = balanceAfter.subtract(balanceBefore);

		Validate.isTrue(money.equals(commission.add(depositedAmount)), "What happened to the other money?! Oh no no no...");
	}

	//	public void withdraw(Account account, Money money, WithdrawStrategy withdrawStrategy) throws NotEnoughFundsException {
	//		withdrawStrategy.withdraw(account, money);
	//	}

}
