package com.devtechnology.api.util;

import com.devtechnology.api.domain.FdaResponse;
import com.devtechnology.api.domain.RxImageResponse;

/**
 * Simple mock for HttpOps for unit tests
 * @author jbnimble
 *
 */
public class HttpOpsSimpleMock implements HttpOps {

	@Override
	public <V> V getMappedFromUlr(String url, Class<V> type) {
		V v = null;
		if (type == FdaResponse.class) {
			v = type.cast(getDummyFdaResponse());
		}
		if (type == RxImageResponse.class) {
			v = type.cast(getDummyRxImageResponse());
		}
		return v;
	}
	
	@Override
	public String getFromUrl(String url) {
		return null;
	}
	
	private FdaResponse getDummyFdaResponse() {
		FdaResponse r = new FdaResponse();
		return r;
	}
	private RxImageResponse getDummyRxImageResponse() {
		RxImageResponse r = new RxImageResponse();
		return r;
	}

	@Override
	public Integer getHttpStatus() {
		return null;
	}
}
