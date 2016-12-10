package com.ail.revolut.app.bank;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public class DefaultWithdrawStrategy implements WithdrawStrategy {

	@Override
	public Money withdraw(Account account, Money amount) throws NotEnoughFundsException {
		Currency accountCurrency = account.getCurrency();
		Money balance = account.getBalance().subtract(amount);
		if (balance.compareTo(new Money("0", accountCurrency)) < 0) {
			throw new NotEnoughFundsException();
		}
		Money commission = new Money("0", accountCurrency);
		account.withdraw(amount.convertTo(accountCurrency), commission);
		return commission;
	}

}
