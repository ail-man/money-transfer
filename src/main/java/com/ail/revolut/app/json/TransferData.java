package com.ail.revolut.app.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferData {
	private Long from;
	private Long to;
	private Long amount;
}
