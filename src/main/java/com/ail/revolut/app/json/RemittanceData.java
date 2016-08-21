package com.ail.revolut.app.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RemittanceData {
	@Getter
	@Setter
	private Long from;
	@Getter
	@Setter
	private Long to;
	@Getter
	@Setter
	private Long amount;
}
