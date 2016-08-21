package com.ail.revolut.app.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ResponseData {
	@Getter
	@Setter
	private Long id;
	@Getter
	@Setter
	private String message;
}
