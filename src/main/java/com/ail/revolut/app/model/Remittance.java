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
	private Long number;

	@Getter
	@Setter
	@Column(nullable = false)
	private Long from;

	@Getter
	@Setter
	@Column(nullable = false)
	private Long to;

	@Getter
	@Setter
	@Column(nullable = false)
	private Long amount;

	@Getter
	@Setter
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date performed;

	@PrePersist
	protected void onCreate() {
		performed = new Date();
	}

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
