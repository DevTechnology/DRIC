package com.devtechnology.api.util;

public interface HttpOps {
	public <V> V getMappedFromUlr(String url, Class<V> type);
	public String getFromUrl(String url);
}
