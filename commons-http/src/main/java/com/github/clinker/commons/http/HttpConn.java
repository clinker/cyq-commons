package com.github.clinker.commons.http;

import java.io.Closeable;
import java.util.Map;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.core5.http.HttpEntity;

/**
 * HTTP操作。
 */
public interface HttpConn extends Closeable {

	/**
	 * 将响应作为字符串。
	 *
	 * @param uri URI
	 * @return 响应字符串
	 */
	String get(String uri);

	/**
	 * 将响应作为字符串。
	 *
	 * @param uri     URI
	 * @param headers 请求头
	 * @return 响应字符串
	 */
	String get(String uri, Map<String, Object> headers);

	/**
	 * 将响应作为字符串。
	 *
	 * @param uri           URI
	 * @param headers       请求头
	 * @param requestConfig RequestConfig
	 * @return 响应字符串
	 */
	String get(final String uri, final Map<String, Object> headers, final RequestConfig requestConfig);

	/**
	 * 请求体是HttpEntity。将响应作为字符串。
	 *
	 * @param uri
	 * @param entity  HttpEntity
	 * @param headers 请求头
	 * @return 响应字符串
	 */
	String post(String uri, HttpEntity entity, Map<String, Object> headers);

	/**
	 * 请求体是字符串。将响应作为字符串。
	 *
	 * @param uri  URI
	 * @param body
	 * @return 响应字符串
	 */
	String post(String uri, String body);

	/**
	 * 请求体是字符串。将响应作为字符串。
	 *
	 * @param uri     URI
	 * @param body
	 * @param headers 请求头
	 * @return 响应字符串
	 */
	String post(String uri, String body, Map<String, Object> headers);

}
