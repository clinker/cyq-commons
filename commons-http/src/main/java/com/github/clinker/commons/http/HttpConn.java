package com.github.clinker.commons.http;

import java.util.Map;

/**
 * HTTP操作。
 */
public interface HttpConn {

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
	 * @param timeout 超时时长，可以为null
	 * @return 响应字符串
	 */
	String get(String url, Map<String, String> headers, HttpTimeout timeout);

	/**
	 * 请求是Form。
	 *
	 * @param url  URL
	 * @param form 请求表单
	 * @return 响应字符串
	 */
	String postForm(String url, Map<String, String> form);

	/**
	 * 请求是Form。
	 *
	 * @param url     URL
	 * @param form    请求表单
	 * @param headers 请求头
	 * @param timeout 超时时长，可以为null
	 * @return 响应字符串
	 */
	String postForm(String url, Map<String, String> form, Map<String, String> headers, HttpTimeout timeout);

	/**
	 * 请求和响应类型都是JSON。
	 *
	 * @param url  URL
	 * @param body 请求体
	 * @return 响应字符串
	 */
	String postJson(String url, String body);

	/**
	 * 请求和响应类型都是JSON。
	 *
	 * @param url     URL
	 * @param body    请求体
	 * @param headers 请求头
	 * @param timeout 超时时长，可以为null
	 * @return 响应字符串
	 */
	String postJson(String url, String body, Map<String, String> headers, HttpTimeout timeout);

}
