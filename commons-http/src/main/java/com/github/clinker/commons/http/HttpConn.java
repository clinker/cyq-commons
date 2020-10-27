package com.github.clinker.commons.http;

import java.io.Closeable;
import java.util.Map;

/**
 * HTTP操作。
 */
public interface HttpConn extends Closeable {

	/**
	 * 将响应作为字符串。响应字符集由实现决定。
	 *
	 * @param uri
	 * @return
	 */
	String get(String uri);

	/**
	 * 请求体是字符串。将响应作为字符串。
	 *
	 * @param uri
	 * @param body
	 * @return 响应字符串
	 */
	String post(String uri, String body);

	/**
	 * 请求体是字符串。将响应作为字符串。
	 *
	 * @param uri
	 * @param body
	 * @param headers 请求头
	 * @return 响应字符串
	 */
	String post(String uri, String body, Map<String, Object> headers);

}
