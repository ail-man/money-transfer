package com.ail.revolut.app.model;

import com.ail.revolut.app.NotEnoughFundsException;
import com.ail.revolut.app.logic.IAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "accounts")
@Entity
@ToString
public class Account implements IAccount {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Setter
	private long balance;

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User owner;

	public void deposit(long amount) {
		long result = balance + amount;
		if (result < 0) {
			throw new RuntimeException("Overflow");
		}
		balance = result;
	}

	public void withdraw(long amount) throws NotEnoughFundsException {
		long result = balance - amount;
		if (result < 0) {
			throw new NotEnoughFundsException();
		}
		balance = result;
	}

}
