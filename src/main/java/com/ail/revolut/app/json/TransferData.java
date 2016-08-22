package com.ail.revolut.app.json;

import lombok.Data;

@Data
public class TransferData {
	private Long from;
	private Long to;
	private Long amount;
}
