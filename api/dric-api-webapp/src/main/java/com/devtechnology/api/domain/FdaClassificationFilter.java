package com.devtechnology.api.domain;

/**
 * Enum used to represent and create the Open FDA filter for 'classification'
 * @author jbnimble
 */
public enum FdaClassificationFilter {
	// using exact match with double quotes
	CLASS1("classification:%22Class+I%22"),
	CLASS2("classification:%22Class+II%22"),
	CLASS3("classification:%22Class+III%22"),
	ALL(null);
	
	private String value;
	FdaClassificationFilter(String value) {
		this.value = value;
	}
	
	public String getFilter() {
		return value;
	}
}
