package com.github.clinker.commons.webmvc;

import javax.servlet.http.HttpServletResponse;

/**
 * 设置HttpServletResponse禁止缓存。
 */
public class ResponseNoCacheUtils {

	/**
	 * 设置响应不缓存。
	 *
	 * @param response 响应
	 */
	public static void noCache(final HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	private ResponseNoCacheUtils() {

	}
}
