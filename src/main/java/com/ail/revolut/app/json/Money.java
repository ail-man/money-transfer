package com.ail.revolut.app.json;

import lombok.Data;

import java.io.Serializable;

@Data
public class Money implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long amount;
}
