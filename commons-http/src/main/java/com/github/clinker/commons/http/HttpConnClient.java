package com.github.clinker.commons.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
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
 *  HttpConn httpConn = new HttpConnClient(5);
 *  httpConn.post(...);
 *  httpConn.get(...);
 *  ...
 *  httpConn.close();
 * </pre>
 */
public class HttpConnClient implements HttpConn {

	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	private static final int DEFAULT_CONNECTIONS = 5;

	private final PoolingHttpClientConnectionManager cm;

	private final CloseableHttpClient httpClient;

	private final Logger log = LoggerFactory.getLogger(HttpConnClient.class);

	public HttpConnClient() {
		this(DEFAULT_CONNECTIONS);
	}

	public HttpConnClient(final int maxConnections) {
		cm = new PoolingHttpClientConnectionManager();
		// 一个HttpConn实例只用于一个route
		// 连接池总数量
		cm.setMaxTotal(maxConnections);
		// 每route的最大连接数
		cm.setDefaultMaxPerRoute(maxConnections);
		httpClient = HttpClients.custom()
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
		log.debug("Get uri: {}", uri);

		String responseString = null;

		int httpStatus = HttpStatus.SC_OK;
		final HttpGet get = new HttpGet(uri);

		try (final CloseableHttpResponse response = httpClient.execute(get)) {
			httpStatus = response.getCode();
			responseString = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
			EntityUtils.consume(response.getEntity());
		} catch (final IOException | ParseException e) {
			log.error("Http get error", e);

			throw new HttpException(e);
		}

		if (httpStatus != HttpStatus.SC_OK) {
			log.error("Get not OK, {}: {}", httpStatus, responseString);
			throw new HttpStatusException(httpStatus, responseString);
		}

		log.debug("Get uri {}, response: {}", uri, responseString);
		return responseString;
	}

	@Override
	public String post(final String uri, final HttpEntity entity, final Map<String, Object> headers) {
		log.debug("Post uri: {}", uri);

		String responseString = null;

		int httpStatus = HttpStatus.SC_OK;
		final HttpPost post = new HttpPost(uri);
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

			throw new HttpException(e);
		}

		if (httpStatus != HttpStatus.SC_OK) {
			log.error("POST not OK: {}, {}", httpStatus, responseString);
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
		return post(uri, new StringEntity(body, DEFAULT_CHARSET), headers);
	}

}
