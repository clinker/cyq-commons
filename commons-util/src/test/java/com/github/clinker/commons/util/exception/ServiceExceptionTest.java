/*******************************************************************************
 * Copyright (c) 陈华(clinker@163.com).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.github.clinker.commons.util.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ServiceExceptionTest {

	private static enum ServiceErrorStub implements ServiceError {
		INVALID, NOT_FOUND
	};

	@Test
	public void testServiceException() throws Exception {
		final ServiceException e = new ServiceException();

		assertNull(e.getCause());
		assertEquals("", e.getErrorCode());
		assertEquals("", e.getMessage());
	}

	@Test
	public void testServiceExceptionServiceError() throws Exception {
		final ServiceException e = new ServiceException(ServiceErrorStub.INVALID);

		assertNull(e.getCause());
		assertEquals(ServiceErrorStub.INVALID.getErrorCode(), e.getErrorCode());
		assertEquals("", e.getMessage());
	}

	@Test
	public void testServiceExceptionServiceErrorString() throws Exception {
		final ServiceException e = new ServiceException(ServiceErrorStub.INVALID, "custom message");

		assertNull(e.getCause());
		assertEquals(ServiceErrorStub.INVALID.getErrorCode(), e.getErrorCode());
		assertEquals("custom message", e.getMessage());
	}

	@Test
	public void testServiceExceptionServiceErrorStringThrowable() throws Exception {
		final ServiceException e = new ServiceException(ServiceErrorStub.INVALID, "custom message",
				new IllegalArgumentException());

		assertEquals(IllegalArgumentException.class, e.getCause()
				.getClass());
		assertEquals(ServiceErrorStub.INVALID.getErrorCode(), e.getErrorCode());
		assertEquals("custom message", e.getMessage());
	}

	@Test
	public void testServiceExceptionStringStringThrowable() throws Exception {
		final ServiceException e = new ServiceException("custom code", "custom message",
				new IllegalArgumentException());

		assertEquals(IllegalArgumentException.class, e.getCause()
				.getClass());
		assertEquals("custom code", e.getErrorCode());
		assertEquals("custom message", e.getMessage());
	}

}
