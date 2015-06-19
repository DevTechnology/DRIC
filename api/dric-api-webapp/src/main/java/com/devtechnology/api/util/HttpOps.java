package com.devtechnology.api.util;

/**
 * Interface to easily unit test by creating mocks for tests
 * @author jbnimble
 *
 */
public interface HttpOps {
	public <V> V getMappedFromUlr(String url, Class<V> type);
	public String getFromUrl(String url);
}
