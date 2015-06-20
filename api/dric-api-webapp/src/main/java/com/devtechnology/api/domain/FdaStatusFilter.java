package com.devtechnology.api.domain;

/**
 * Enum used to represent and create the Open FDA filter for 'status'
 * @author jbnimble
 */
public enum FdaStatusFilter {
	ONGOING("status:Ongoing"),
	COMPLETED("status:Completed"),
	TERMINATED("status:Terminated"),
	ALL(null);
	
	private String value;
	FdaStatusFilter(String value) {
		this.value = value;
	}
	
	public String getFilter() {
		return value;
	}
}
