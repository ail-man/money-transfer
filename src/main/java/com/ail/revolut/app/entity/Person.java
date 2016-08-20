package com.ail.revolut.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tblperson")
public class Person implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	@Column(name = "name", nullable = false)
	private String Name;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}