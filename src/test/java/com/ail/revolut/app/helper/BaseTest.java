package com.ail.revolut.app.helper;

import static org.junit.Assert.fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

	protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

	protected void assertTestFails(TestOperation testOperation, Class<?> exceptionClass) throws Exception {
		try {
			testOperation.perform();
			fail("should fail");
		} catch (Exception e) {
			if (exceptionClass.isInstance(e)) {
				logger.debug(e.getMessage());
				return;
			}
			throw e;
		}
	}

}
