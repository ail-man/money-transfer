package com.ail.revolut.app.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Money implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long amount;
}
