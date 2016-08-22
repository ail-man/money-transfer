package com.ail.revolut.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "accounts")
@Entity
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Setter
	private Long balance;

	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User owner;

	@Override
	public String toString() {
		return "Account(" +
				"id=" + id +
				", balance=" + balance +
				", owner=" + (owner != null ? owner.getId() : null) +
				')';
	}
}
