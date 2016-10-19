package com.ail.revolut.app.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
	private BigInteger amount;

	@Column(nullable = false)
	private Date performed;

	public Date getPerformed() {
		return performed != null ? (Date) performed.clone() : null;
	}

	public void setPerformed(Date performed) {
		this.performed = performed != null ? (Date) performed.clone() : null;
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
