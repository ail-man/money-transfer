package com.ail.revolut.app.rest.common;

import com.ail.revolut.app.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRestService {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractRestService.class);

	protected ResponseData handleRequest(RequestHandler requestHandler) {
		try {
			return requestHandler.handleRequest();
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getMessage();
			logger.trace(msg, e);
			ResponseData responseData = new ResponseData();
			responseData.setMessage(msg);
			return responseData;
		}
	}
}
