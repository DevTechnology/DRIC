package com.devtechnology.api.domain;

import java.util.ArrayList;
import java.util.List;

public class RxImageResponse {
	private RxImageStatus replyStatus;
	private List<RxImageResult> nlmRxImages;
	public RxImageStatus getReplyStatus() {
		return replyStatus;
	}
	public void setReplyStatus(RxImageStatus replyStatus) {
		this.replyStatus = replyStatus;
	}
	public List<RxImageResult> getNlmRxImages() {
		nlmRxImages = (nlmRxImages == null) ? new ArrayList<RxImageResult>() : nlmRxImages;
		return nlmRxImages;
	}
	public void setNlmRxImages(List<RxImageResult> nlmRxImages) {
		this.nlmRxImages = nlmRxImages;
	}
}
