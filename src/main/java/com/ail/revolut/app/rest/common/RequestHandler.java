package com.ail.revolut.app.rest.common;

import com.ail.revolut.app.dto.ResponseData;

public interface RequestHandler {

	ResponseData handleRequest() throws Exception;

}
