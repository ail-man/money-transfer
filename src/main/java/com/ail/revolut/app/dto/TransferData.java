package com.ail.revolut.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class TransferData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long from;
	private Long to;
	private BigDecimal amount;
}
