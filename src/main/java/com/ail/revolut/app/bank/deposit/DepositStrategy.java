package com.ail.revolut.app.bank.deposit;

import com.ail.revolut.app.bank.Account;
import com.ail.revolut.app.bank.Money;

public interface DepositStrategy {

	Money deposit(Account account, Money money);

}
