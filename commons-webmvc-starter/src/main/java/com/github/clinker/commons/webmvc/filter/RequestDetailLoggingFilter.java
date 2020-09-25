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
package com.github.clinker.commons.webmvc.filter;

import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import com.github.clinker.commons.webmvc.RemoteAddressUtils;

/**
 * 记录HTTP request信息。
 * <p/>
 * 用法： 日志级别：TRACE。
 * 
 * <pre>
 * &#64;Configuration
 * public class SpringWebConfig {
 *
 * 	&#64;Bean
 * 	protected Filter requestDetailLoggingFilter() {
 * 		return new RequestDetailLoggingFilter();
 * 	}
 * }
 *
 * </pre>
 *
 */
public class RequestDetailLoggingFilter extends AbstractRequestLoggingFilter {

	private static final Logger log = LoggerFactory.getLogger(RequestDetailLoggingFilter.class);

	private static final int MAX_PAYLOAD_LENGTH = 500;

	@Override
	protected void afterRequest(final HttpServletRequest request, final String message) {
		log.trace(message);
	}

	@Override
	protected void beforeRequest(final HttpServletRequest request, final String message) {
		log.trace(message);
	}

	@Override
	protected String createMessage(final HttpServletRequest request, final String prefix, final String suffix) {
		final String headers = Collections.list(request.getHeaderNames())
				.stream()
				.map(name -> name + ":" + request.getHeader(name))
				.collect(Collectors.joining(", ", "{", "}"));

		final StringBuilder msg = new StringBuilder();
		msg.append('[');
		final String clientAddr = RemoteAddressUtils.resolve(request);
		if (clientAddr != null) {
			msg.append("client=")
					.append(clientAddr)
					.append(';');
		}
		msg.append("method=")
				.append(request.getMethod());
		msg.append(";content type=")
				.append(request.getContentType());
		msg.append(";encoding=")
				.append(request.getCharacterEncoding());
		msg.append(";headers=")
				.append(headers);
		msg.append(']');

		return super.createMessage(request, prefix, suffix) + msg.toString();
	}

	@Override
	protected int getMaxPayloadLength() {
		return MAX_PAYLOAD_LENGTH;
	}

	@Override
	protected boolean isIncludeClientInfo() {
		return true;
	}

	@Override
	protected boolean isIncludePayload() {
		return true;
	}

	@Override
	protected boolean isIncludeQueryString() {
		return true;
	}

	@Override
	protected boolean shouldLog(final HttpServletRequest request) {
		return log.isTraceEnabled();
	}

}
