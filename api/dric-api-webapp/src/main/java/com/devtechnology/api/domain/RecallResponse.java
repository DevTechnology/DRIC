package com.devtechnology.api.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO representing DRIC response object
 * @author jbnimble
 *
 */
@XmlRootElement
public class RecallResponse {
	private FdaMeta meta;
	private List<RecallItem> recalls;
	private FdaError error;
	
	public FdaMeta getMeta() {
		return meta;
	}
	public void setMeta(FdaMeta meta) {
		this.meta = meta;
	}
	public List<RecallItem> getRecalls() {
		recalls = (recalls == null) ? new ArrayList<RecallItem>() : recalls;
		return recalls;
	}
	public void setRecalls(List<RecallItem> recalls) {
		this.recalls = recalls;
	}
	public FdaError getError() {
		return error;
	}
	public void setError(FdaError error) {
		this.error = error;
	}
}
