/**
 * 
 */
package com.zhubao.b2b.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 
 */
public abstract class RestfulClient {
	private String userid;
	private String token;
	private HttpUriRequest httpUriRequest;
	private HttpEntity httpEntity;
	private HttpEntity responseEntity;
	private int responseStatus;


	public RestfulClient(String userid, String token) {
		this.userid = userid;
		this.token = token;
	}

	/**
	 */
	public RestfulClient() {
		this.userid = null;
		this.token = null;
	}

	protected abstract String getIp();

	protected abstract int getPort();

	protected void execute() {
		HttpClient client = new DefaultHttpClient();
		try {
			setAuthHeader();

			if (httpUriRequest instanceof HttpEntityEnclosingRequestBase) {
				((HttpEntityEnclosingRequest) httpUriRequest)
						.setEntity(httpEntity);
			}

			System.out.println("http " + httpUriRequest.getMethod()
					+ " request url>>>");
			System.out.println(httpUriRequest.getURI());

			System.out.println("http request header>>>");
			for (Header header : httpUriRequest.getAllHeaders()) {
				System.out.println(header.getName() + ":" + header.getValue());
			}

			HttpResponse httpResponse = null;

			httpResponse = client.execute(new HttpHost(getIp(), getPort()),
					httpUriRequest);
			System.out.println("http response status>>>");
			System.out.println(httpResponse.getStatusLine());

			System.out.println("http response content>>>");
			responseEntity = httpResponse.getEntity();
			responseStatus = httpResponse.getStatusLine().getStatusCode();

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return response status code
	 */
	public int getResponseStatus() {
		return responseStatus;
	}

	/**
	 * @return response entity
	 */
	public HttpEntity getResponseEntity() {
		return responseEntity;
	}

	/**
	 * @param httpEntity
	 *            http request entity
	 */
	public void setHttpContent(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}

	/**
	 * @param contentType
	 *            content type
	 */
	public void setHttpContentType(String contentType) {
		httpUriRequest.addHeader("Content-type", contentType);
	}

	/**
	 * @param key
	 *            header name
	 * @param value
	 *            header content
	 */
	public void setHeader(String key, String value) {
		httpUriRequest.addHeader(key, value);
	}

	/**
	 * @param httpUriRequest
	 *            http request object
	 */
	public void setHttpMethod(HttpUriRequest httpUriRequest) {
		this.httpUriRequest = httpUriRequest;
	}

	protected void setUserid(String uesrid) {
		this.userid = uesrid;
	}

	protected void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the appId
	 */
	protected String getUserid() {
		return this.userid;
	}

	/**
	 * @return the appKey
	 */
	protected String getToken() {
		return this.token;
	}

	private void setAuthHeader() {
		if (this.userid != null && this.token != null) {
			httpUriRequest.addHeader("uid", this.userid);
            httpUriRequest.addHeader("token", this.token);
		}
	}
}