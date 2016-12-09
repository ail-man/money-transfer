package com.ail.revolut.app.bank;

import com.ail.revolut.app.bank.deposit.DepositStrategy;
import org.apache.commons.lang3.Validate;

public class Bank {

	public Account createAccount(Person person, Currency currency) {
		return new Account(person, currency);
	}

	public Money deposit(Account account, Money money, DepositStrategy depositStrategy) {
		Money balanceBefore = account.getBalance();
		Money commission = depositStrategy.deposit(account, money);
		Money balanceAfter = account.getBalance();

		validateDeposit(money, balanceBefore, commission, balanceAfter);

		return commission;
	}

	//	public void withdraw(Account account, Money money, WithdrawStrategy withdrawStrategy) throws NotEnoughFundsException {
	//		withdrawStrategy.withdraw(account, money);
	//	}

	private void validateDeposit(Money money, Money balanceBefore, Money commission, Money balanceAfter) {
		Money depositedAmount = balanceAfter.subtract(balanceBefore);
		Validate.isTrue(money.equals(commission.add(depositedAmount)), "What happened to the other money?! Oh no no no...");
	}

}
