package com.devtechnology.api.domain;

/**
 * POJO representing RxImage JSON object
 * @author jbnimble
 *
 */public class RxImageResult {
	private Long id;
	private String ndc11;
	private Long rxcui;
	private String splSetId;
	private String acqDate;
	private String name;
	private String labeler;
	private String imageUrl;
	private Long imageSize;
	private String attribution;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNdc11() {
		return ndc11;
	}
	public void setNdc11(String ndc11) {
		this.ndc11 = ndc11;
	}
	public Long getRxcui() {
		return rxcui;
	}
	public void setRxcui(Long rxcui) {
		this.rxcui = rxcui;
	}
	public String getSplSetId() {
		return splSetId;
	}
	public void setSplSetId(String splSetId) {
		this.splSetId = splSetId;
	}
	public String getAcqDate() {
		return acqDate;
	}
	public void setAcqDate(String acqDate) {
		this.acqDate = acqDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabeler() {
		return labeler;
	}
	public void setLabeler(String labeler) {
		this.labeler = labeler;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Long getImageSize() {
		return imageSize;
	}
	public void setImageSize(Long imageSize) {
		this.imageSize = imageSize;
	}
	public String getAttribution() {
		return attribution;
	}
	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}
}
