package com.devtechnology.api.util;

/**
 * Interface to easily unit test by creating mocks for tests
 * @author jbnimble
 *
 */
public interface HttpOps {
	/**
	 * perform a GET on the given 'url' and convert the raw JSON to a POJO
	 */
	public <V> V getMappedFromUlr(String url, Class<V> type);
	/**
	 * perform a GET on the given 'url' and return the raw result as a String
	 * @param url
	 * @return
	 */
	public String getFromUrl(String url);
	/**
	 * get the remote HTTP status
	 * @return
	 */
	public Integer getHttpStatus();
}
