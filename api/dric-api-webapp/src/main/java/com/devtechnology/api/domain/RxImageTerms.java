package com.devtechnology.api.domain;

/**
 * POJO representing RxImage JSON object
 * @author jbnimble
 *
 */public class RxImageTerms {
	private String ndc;
	private String rxcui;
	public String getNdc() {
		return ndc;
	}
	public void setNdc(String ndc) {
		this.ndc = ndc;
	}
	public String getRxcui() {
		return rxcui;
	}
	public void setRxcui(String rxcui) {
		this.rxcui = rxcui;
	}
}
