package com.ail.revolut.app.bank;

import com.ail.revolut.app.exception.NotEnoughFundsException;

public interface Bank {

	Account createAccount(Person person, Currency currency);

	Money getBalance(Account account);

	Money deposit(Account account, Money amount, DepositStrategy depositStrategy);

	Money withdraw(Account account, Money amount, WithdrawStrategy withdrawStrategy) throws NotEnoughFundsException;

	Money transfer(Account fromAccount, Account toAccount, Money amount, TransferStrategy transferStrategy) throws NotEnoughFundsException;

}
