package com.ail.revolut.app.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "users")
@Entity
@ToString
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private long id;

	@Column(name = "name", nullable = false, unique = true, length = 50)
	@Getter
	@Setter
	private String name;

	@OneToMany
	@Getter
	@Setter
	private List<Account> account;
}
