package com.ail.revolut.app.bank.deposit;

import com.ail.revolut.app.bank.Account;
import com.ail.revolut.app.bank.Money;

public class DefaultDepositStrategy implements DepositStrategy {

	@Override
	public Money deposit(Account account, Money money) {
		account.setBalance(account.getBalance().add(money));
		return new Money("0", account.getCurrency());
	}

}
