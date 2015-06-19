package com.devtechnology.api.domain;

/**
 * POJO representing RxImage JSON object
 * @author jbnimble
 *
 */public class RxImageStatus {
	private Boolean success;
	private String date;
	private Integer imageCount;
	private Integer totalImageCount;
	private RxImageTerms matchedTerms;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getImageCount() {
		return imageCount;
	}
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}
	public Integer getTotalImageCount() {
		return totalImageCount;
	}
	public void setTotalImageCount(Integer totalImageCount) {
		this.totalImageCount = totalImageCount;
	}
	public RxImageTerms getMatchedTerms() {
		return matchedTerms;
	}
	public void setMatchedTerms(RxImageTerms matchedTerms) {
		this.matchedTerms = matchedTerms;
	}
}
