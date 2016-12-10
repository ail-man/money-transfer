package com.ail.revolut.app.bank;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface TransferStrategy {

	Money transfer(Account fromAccount, Account toAccount, Money amount) throws NotEnoughFundsException;

	Money transfer(Bank fromBank, Account fromAccount, Bank toBank, Account toAccount) throws NotEnoughFundsException;
}
