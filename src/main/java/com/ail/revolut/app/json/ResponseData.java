package com.ail.revolut.app.json;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@XmlRootElement
public class ResponseData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String value;
	private String message;
}
