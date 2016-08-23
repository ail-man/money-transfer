package com.ail.revolut.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class TransferData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long from;
	private Long to;
	private Long amount;
}