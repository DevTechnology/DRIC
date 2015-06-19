package com.devtechnology.api.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO representing FDA JSON object
 * @author jbnimble
 *
 */
public class FdaResponse {
	private FdaMeta meta;
	private List<FdaResults> results;
	private FdaError error;
	
	public FdaMeta getMeta() {
		return meta;
	}
	public void setMeta(FdaMeta meta) {
		this.meta = meta;
	}
	public List<FdaResults> getResults() {
		results = (results == null) ? new ArrayList<FdaResults>() : results;
		return results;
	}
	public void setResults(List<FdaResults> results) {
		this.results = results;
	}
	public FdaError getError() {
		return error;
	}
	public void setError(FdaError error) {
		this.error = error;
	}
}
