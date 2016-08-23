package com.ail.revolut.app.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long from;
	private Long to;
	private Long amount;
}
