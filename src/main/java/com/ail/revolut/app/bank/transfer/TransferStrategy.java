package com.ail.revolut.app.bank.transfer;

import com.ail.revolut.app.bank.Account;
import com.ail.revolut.app.bank.Money;
import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface TransferStrategy {

	Money transfer(Account fromAccount, Account toAccount, Money amount) throws NotEnoughFundsException;

}
