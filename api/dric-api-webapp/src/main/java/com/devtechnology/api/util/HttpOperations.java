package com.devtechnology.api.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpOperations {
	Logger logger = Logger.getLogger(HttpOperations.class);
	public String getFromUrl(String url) {
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			logger.info("Executing request " + httpget.getRequestLine());
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			result = httpclient.execute(httpget, responseHandler);
		} catch (ClientProtocolException e) {
			logger.error("Failed using url="+url, e);
		} catch (IOException e) {
			logger.error("Failed using url="+url, e);
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					logger.error("Failed to close httpclient using url="+url+" due to "+e.getMessage());
				}
			}
		}
		return result;
	}
}
