package com.ail.revolut.app.bank.deposit;

import com.ail.revolut.app.bank.Account;
import com.ail.revolut.app.bank.Money;

public class DefaultDepositStrategy implements DepositStrategy {

	@Override
	public Money deposit(Account account, Money amount) {
		Money commission = new Money("0", account.getCurrency());
		account.deposit(amount, commission);
		return commission;
	}

}
