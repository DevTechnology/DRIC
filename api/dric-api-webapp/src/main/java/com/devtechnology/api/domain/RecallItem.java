package com.devtechnology.api.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Domain object representing a set of recall data
 * @author jbnimble
 */
@XmlRootElement
public class RecallItem {
	private String severity;
	private List<String> product;
	private String receivedate;
	private List<String> images;
	private List<String> drugs;// will eventually be another domain object with fields
	
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public List<String> getProduct() {
		product = (product == null) ? new ArrayList<String>() : product;
		return product;
	}
	public void setProduct(List<String> product) {
		this.product = product;
	}
	public String getReceivedate() {
		return receivedate;
	}
	public void setReceivedate(String receivedate) {
		this.receivedate = receivedate;
	}
	public List<String> getImages() {
		images = (images == null) ? new ArrayList<String>() : images;
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public List<String> getDrugs() {
		return drugs;
	}
	public void setDrugs(List<String> drugs) {
		this.drugs = drugs;
	}
}
