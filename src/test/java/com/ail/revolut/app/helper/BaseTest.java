package com.ail.revolut.app.helper;

import static org.junit.Assert.fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

	protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

	protected void assertOperationFails(TestOperation testOperation, Class<?>... exceptionClasses) {
		try {
			testOperation.perform();
			fail("should fail");
		} catch (Exception e) {
			for (Class<?> exceptionClass : exceptionClasses) {
				if (e.getClass().equals(exceptionClass)) {
					logger.debug(e.getMessage());
					return;
				}
			}
			fail("unexpected exception");
		}
	}

}
