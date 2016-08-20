package com.ail.revolut.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transaction {
	private static final Logger logger = LoggerFactory.getLogger(Transaction.class);
	private final Account from;
	private final Account to;
	private final long amount;

	public Transaction(Account from, Account to, long amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public void commit() {
		try {
			from.withdraw(amount);
			to.deposit(amount);
		} catch (NotEnoughFundsException e) {
			logger.debug(e.getMessage(), e);
		}
	}
}
