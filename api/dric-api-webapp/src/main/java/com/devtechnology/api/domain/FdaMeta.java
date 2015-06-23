package com.devtechnology.api.domain;

/**
 * POJO representing FDA JSON object
 * @author jbnimble
 *
 */
public class FdaMeta {
	private String disclaimer;
	private String license;
	private String last_updated;
	private FdaMetaResults results;
	private Integer httpStatus;
	
	public Integer getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}
	public FdaMetaResults getResults() {
		return results;
	}
	public void setResults(FdaMetaResults results) {
		this.results = results;
	}
}
