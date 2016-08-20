package com.ail.revolut.app.model;


import com.ail.revolut.app.api.IUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "users")
@Entity
@ToString(exclude = "accounts")
public class User implements Serializable, IUser {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Setter
	@Column(name = "name", nullable = false, unique = true, length = 50)
	private String name;

	@Getter
	@Setter
	@OneToMany(mappedBy = "owner")
	private List<Account> accounts;
}
