package com.ail.revolut.app.bank;

public interface DepositStrategy {

	Money deposit(Account account, Money amount);

}
