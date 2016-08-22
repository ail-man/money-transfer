package com.ail.revolut.app.json;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseDataTest {

	@Test
	public void testToString() throws Exception {
		ResponseData responseData = new ResponseData();

		assertThat(responseData.toString(), equalTo("ResponseData(value=null, message=null)"));

		responseData.setValue("1244");
		responseData.setMessage("Some message");

		assertThat(responseData.toString(), equalTo("ResponseData(value=1244, message=Some message)"));
	}

}
