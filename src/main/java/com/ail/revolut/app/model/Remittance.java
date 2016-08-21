package com.ail.revolut.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "remittances")
@Entity
@ToString
public class Remittance implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Setter
	@Column(nullable = false)
	private Long fromId;

	@Getter
	@Setter
	@Column(nullable = false)
	private Long toId;

	@Getter
	@Setter
	@Column(nullable = false)
	private Long amount;

	@Getter
	@Setter
	@Column(nullable = false)
	private Date performed;

	@Override
	public Remittance clone() {
		Remittance clone;
		try {
			clone = (Remittance) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		clone.performed = (Date) performed.clone();
		return clone;
	}
}
