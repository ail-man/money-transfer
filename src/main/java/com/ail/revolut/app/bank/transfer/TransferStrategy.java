package com.ail.revolut.app.bank.transfer;

import com.ail.revolut.app.bank.Account;
import com.ail.revolut.app.bank.Money;

public interface TransferStrategy {

	Money transfer(Account fromAccount, Account toAccount, Money amount);

}
