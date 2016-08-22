package com.ail.revolut.app.json;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseDataTest {

	@Test
	public void testToString() {
		ResponseData responseData = new ResponseData();

		assertThat(responseData.toString(), equalTo("ResponseData(id=null, message=null)"));

		responseData.setId(1244L);
		responseData.setMessage("Some message");

		assertThat(responseData.toString(), equalTo("ResponseData(id=1244, message=Some message)"));
	}

}
