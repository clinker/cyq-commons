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
	 * @param url URL
	 * @return 响应字符串
	 */
	String get(String url);

	/**
	 * 将响应作为字符串。
	 *
	 * @param url     URL
	 * @param headers 请求头
	 * @return 响应字符串
	 */
	String get(String url, Map<String, Object> headers);

	/**
	 * 将响应作为字符串。
	 *
	 * @param url     URL
	 * @param headers 请求头
	 * @param timeout 超时时长，可以为null
	 * @return 响应字符串
	 */
	String get(String url, Map<String, Object> headers, HttpTimeout timeout);

	/**
	 * 请求体是字符串。将响应作为字符串。
	 *
	 * @param url     URL
	 * @param entity  HttpEntity
	 * @param headers 请求头
	 * @return 响应字符串
	 */
	String post(String url, HttpEntity entity, Map<String, Object> headers);

	/**
	 * 请求体是字符串。将响应作为字符串。
	 *
	 * @param url           URL
	 * @param entity        HttpEntity
	 * @param headers       请求头
	 * @param requestConfig RequestConfig
	 * @return 响应字符串
	 */
	String post(String url, HttpEntity entity, Map<String, String> headers, RequestConfig requestConfig);

	/**
	 * 请求体是字符串。将响应作为字符串。
	 *
	 * @param url  URL
	 * @param body
	 * @return 响应字符串
	 */
	String post(String url, String body);

	/**
	 * 请求体是字符串。将响应作为字符串。
	 *
	 * @param url           URL
	 * @param entity        HttpEntity
	 * @param headers       请求头
	 * @param requestConfig RequestConfig
	 * @return 响应字符串
	 */
	String post(String url, String body, Map<String, Object> headers, RequestConfig requestConfig);

	/**
	 * 请求体是字符串。将响应作为字符串。
	 *
	 * @param url     URL
	 * @param body
	 * @param headers 请求头
	 * @return 响应字符串
	 */
	String post(String url, String body, Map<String, String> headers);

}
