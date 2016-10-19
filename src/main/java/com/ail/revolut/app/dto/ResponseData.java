package com.ail.revolut.app.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class ResponseData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String value;
	private String message;
}
