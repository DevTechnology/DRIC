package com.devtechnology.api.domain;

/**
 * POJO representing FDA JSON object
 * @author jbnimble
 *
 */
public class FdaMetaResults {
	private Integer skip;
	private Integer limit;
	private Integer total;
	
	public Integer getSkip() {
		return skip;
	}
	public void setSkip(Integer skip) {
		this.skip = skip;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
