package com.ail.revolut.app.bank.withdraw;

import com.ail.revolut.app.bank.Account;
import com.ail.revolut.app.bank.Money;

public interface WithdrawStrategy {

	Money withdraw(Account account, Money amount);

}
