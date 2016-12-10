package com.ail.revolut.app.bank;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface WithdrawStrategy {

	Money withdraw(Account account, Money amount) throws NotEnoughFundsException;

}
