package com.ail.revolut.app.bank;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public class DefaultWithdrawStrategy implements WithdrawStrategy {

	@Override
	public Money withdraw(Account account, Money amount) throws NotEnoughFundsException {
		Currency accountCurrency = account.getCurrency();
		Money balance = account.getBalance().subtract(amount);
		if (balance.signum() < 0) {
			throw new NotEnoughFundsException();
		}
		Money commission = Money.zero(accountCurrency);
		account.withdraw(amount.convertTo(accountCurrency), commission);
		return commission;
	}

}
