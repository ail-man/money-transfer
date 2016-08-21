package com.ail.revolut.app.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Response {

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String message;
}
