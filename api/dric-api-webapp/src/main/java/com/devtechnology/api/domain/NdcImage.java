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
public class NdcImage {
	private String ndc;
	private List<String> url;
	
	public String getNdc() {
		return ndc;
	}
	public void setNdc(String ndc) {
		this.ndc = ndc;
	}
	public List<String> getUrl() {
		url = (url == null) ? new ArrayList<String>() : url;
		return url;
	}
	public void setUrl(List<String> url) {
		this.url = url;
	}
}
