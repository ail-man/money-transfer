package com.ail.revolut.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "accounts")
@Entity
@ToString
public class Account {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private long id;

	@ManyToOne
	@Getter
	@Setter
	private User user;

}
