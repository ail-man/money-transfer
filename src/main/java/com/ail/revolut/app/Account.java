package com.ail.revolut.app;

public class Account {
	private final long id;
	private long ballance;

	public Account(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public long getBallance() {
		return ballance;
	}

	public void deposit(long amount) {
		long result = ballance + amount;
		if (result < 0) {
			throw new RuntimeException("Overflow");
		}
		ballance = result;
	}

	public void withdraw(long amount) throws NotEnoughFundsException {
		long result = ballance - amount;
		if (result < 0) {
			throw new NotEnoughFundsException();
		}
		ballance = result;
	}
}
