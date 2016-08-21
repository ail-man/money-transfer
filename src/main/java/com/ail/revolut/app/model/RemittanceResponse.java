package com.ail.revolut.app.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RemittanceResponse {

	@Getter
	@Setter
	private Long number;

	@Getter
	@Setter
	private String message;
}
