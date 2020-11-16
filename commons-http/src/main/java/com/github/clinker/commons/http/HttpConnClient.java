package com.github.clinker.commons.http;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 使用Apache HttpComponents Client实现。
 * <p/>
 * 特点：
 * <ul>
 * <li>使用连接池；</li>
 * <li>并发连接数量可控；</li>
 * <li>HTTP层出错后，例如IO，抛出HttpException；</li>
 * <li>HTTP状态码不是200时，抛出HttpStatusException。</li>
 * </ul>
 * <p/>
 * 使用方法：
 *
 * <pre>
 *  HttpConn httpConn = new HttpConnClient(5,100);
 *  httpConn.post(...);
 *  httpConn.get(...);
 *  ...
 *  httpConn.close();
 * </pre>
 */
public class HttpConnClient implements HttpConn {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private final Logger log = LoggerFactory.getLogger(HttpConnClient.class);

	private final OkHttpClient client = new OkHttpClient();

	private OkHttpClient buildClient(final HttpTimeout timeout) {
		if (timeout == null) {
			return client;
		}

		final OkHttpClient.Builder clientBuilder = client.newBuilder();
		if (timeout.getConnect() != null) {
			clientBuilder.connectTimeout(timeout.getConnect());
		}
		if (timeout.getRead() != null) {
			clientBuilder.readTimeout(timeout.getRead());
		}
		if (timeout.getWrite() != null) {
			clientBuilder.writeTimeout(timeout.getWrite());
		}

		return clientBuilder.build();
	}

	@Override
	public String get(final String url) {
		return get(url, null, null);
	}

	@Override
	public String get(final String url, final Map<String, String> headers, final HttpTimeout timeout) {
		log.debug("Get url: {}", url);

		final OkHttpClient client = buildClient(timeout);

		final Request.Builder requestBuilder = new Request.Builder().url(url);
		if (headers != null) {
			headers.entrySet()
					.forEach(entry -> requestBuilder.addHeader(entry.getKey(), entry.getValue()));
		}

		return sendHttp(url, client, requestBuilder);
	}

	@Override
	public String postForm(final String url, final Map<String, String> form) {
		return postForm(url, form, null, null);
	}

	@Override
	public String postForm(final String url, final Map<String, String> form, final Map<String, String> headers,
			final HttpTimeout timeout) {
		log.debug("Post form url: {}", url);

		final OkHttpClient client = buildClient(timeout);

		final Request.Builder requestBuilder = new Request.Builder().url(url);
		if (headers != null) {
			headers.entrySet()
					.forEach(entry -> requestBuilder.addHeader(entry.getKey(), entry.getValue()));
		}

		final FormBody.Builder formBodyBuilder = new FormBody.Builder();
		if (form != null && !form.isEmpty()) {
			form.entrySet()
					.forEach(entry -> formBodyBuilder.add(entry.getKey(), entry.getValue()));
		}

		requestBuilder.post(formBodyBuilder.build());

		return sendHttp(url, client, requestBuilder);
	}

	@Override
	public String postJson(final String url, final String body) {
		return postJson(url, body, null, null);
	}

	@Override
	public String postJson(final String url, final String body, final Map<String, String> headers,
			final HttpTimeout timeout) {
		log.debug("Post json url: {}", url);

		final OkHttpClient client = buildClient(timeout);

		final Request.Builder requestBuilder = new Request.Builder().url(url);
		if (headers != null) {
			headers.entrySet()
					.forEach(entry -> requestBuilder.addHeader(entry.getKey(), entry.getValue()));
		}
		requestBuilder.post(RequestBody.create(body, JSON));

		return sendHttp(url, client, requestBuilder);
	}

	private String sendHttp(final String url, final OkHttpClient client, final Request.Builder requestBuilder) {
		final Request request = requestBuilder.build();
		String responseString = null;
		try (Response response = client.newCall(request)
				.execute()) {
			responseString = response.body()
					.string();

			if (!response.isSuccessful()) {
				log.error("Http not OK, url: {}\nstatus: {}, {}", url, response.code(), responseString);
				throw new HttpStatusException(response.code(), responseString);
			}
			log.debug("Http url {}, response: {}", url, responseString);

			return responseString;

		} catch (final IOException e) {
			log.error("Http post error", e);

			throw new HttpIoException(responseString, e);
		}
	}

}
