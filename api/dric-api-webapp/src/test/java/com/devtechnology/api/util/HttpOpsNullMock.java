package com.devtechnology.api.util;

/**
 * HttpOps that always returns null
 * @author jbnimble
 */
public class HttpOpsNullMock implements HttpOps {

	@Override
	public <V> V getMappedFromUlr(String url, Class<V> type) {
		return null;
	}

	@Override
	public String getFromUrl(String url) {
		return null;
	}

	@Override
	public Integer getHttpStatus() {
		return null;
	}

}
