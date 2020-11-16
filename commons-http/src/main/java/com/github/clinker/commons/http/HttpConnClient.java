package com.github.clinker.commons.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.io.CloseMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	private final PoolingHttpClientConnectionManager cm;

	private final CloseableHttpClient httpClient;

	private final Logger log = LoggerFactory.getLogger(HttpConnClient.class);

	public HttpConnClient() {
		this(10, 200);
	}

	public HttpConnClient(final int maxPerRoute, final int maxTotal) {
		cm = new PoolingHttpClientConnectionManager();
		// 连接池总数量
		cm.setMaxTotal(maxTotal);
		// 每route的最大连接数
		cm.setDefaultMaxPerRoute(maxPerRoute);
		httpClient = HttpClients.custom()
				.disableCookieManagement()
				.setConnectionManager(cm)
				.build();
	}

	@Override
	public void close() {
		if (httpClient != null) {
			httpClient.close(CloseMode.IMMEDIATE);
		}

		if (cm != null) {
			cm.close(CloseMode.IMMEDIATE);
		}
	}

	@Override
	public String get(final String uri) {
		return get(uri, null, null);
	}

	@Override
	public String get(final String uri, final Map<String, Object> headers) {
		return get(uri, headers, null);
	}

	@Override
	public String get(final String uri, final Map<String, Object> headers, final RequestConfig requestConfig) {
		log.debug("Get uri: {}", uri);

		String responseString = null;

		int httpStatus = HttpStatus.SC_OK;
		final HttpGet get = new HttpGet(uri);
		if (requestConfig != null) {
			get.setConfig(requestConfig);
		}
		if (headers != null) {
			headers.entrySet()
					.forEach(entry -> get.setHeader(entry.getKey(), entry.getValue()));
		}

		try (final CloseableHttpResponse response = httpClient.execute(get)) {
			httpStatus = response.getCode();
			responseString = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
			EntityUtils.consume(response.getEntity());
		} catch (final IOException | ParseException e) {
			log.error("Http get error", e);

			throw new HttpIoException(responseString, e);
		}

		if (httpStatus != HttpStatus.SC_OK) {
			log.error("Get not OK, uri: {}\nstatus: {}, {}", uri, httpStatus, responseString);
			throw new HttpStatusException(httpStatus, responseString);
		}

		log.debug("Get uri {}, response: {}", uri, responseString);
		return responseString;
	}

	@Override
	public String post(final String uri, final HttpEntity entity, final Map<String, Object> headers) {
		return post(uri, entity, headers, null);
	}

	@Override
	public String post(final String uri, final HttpEntity entity, final Map<String, Object> headers,
			final RequestConfig requestConfig) {
		log.debug("Post uri: {}", uri);

		String responseString = null;

		int httpStatus = HttpStatus.SC_OK;
		final HttpPost post = new HttpPost(uri);
		if (requestConfig != null) {
			post.setConfig(requestConfig);
		}
		post.setEntity(entity);
		if (headers != null) {
			headers.entrySet()
					.forEach(entry -> post.setHeader(entry.getKey(), entry.getValue()));
		}

		try (CloseableHttpResponse response = httpClient.execute(post)) {
			httpStatus = response.getCode();
			responseString = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
			EntityUtils.consume(response.getEntity());
		} catch (final IOException | ParseException e) {
			log.error("Http post error", e);

			throw new HttpIoException(responseString, e);
		}

		if (httpStatus != HttpStatus.SC_OK) {
			log.error("POST not OK, uri: {}\nstatus: {}, {}", uri, httpStatus, responseString);
			throw new HttpStatusException(httpStatus, responseString);
		}

		log.debug("Post uri {}, response: {}", uri, responseString);
		return responseString;
	}

	@Override
	public String post(final String uri, final String body) {
		return post(uri, body, null);
	}

	@Override
	public String post(final String uri, final String body, final Map<String, Object> headers) {
		return post(uri, body, headers);
	}

	@Override
	public String post(final String uri, final String body, final Map<String, Object> headers,
			final RequestConfig requestConfig) {
		return post(uri, new StringEntity(body, DEFAULT_CHARSET), headers, requestConfig);
	}

}
