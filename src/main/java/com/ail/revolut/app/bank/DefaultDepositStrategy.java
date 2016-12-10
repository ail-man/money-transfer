package com.ail.revolut.app.bank;

public class DefaultDepositStrategy implements DepositStrategy {

	@Override
	public Money deposit(Account account, Money amount) {
		Money commission = Money.zero(account.getCurrency());
		account.deposit(amount, commission);
		return commission;
	}

}
