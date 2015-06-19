package com.devtechnology.api.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.devtechnology.api.domain.NdcImage;
import com.devtechnology.api.domain.RxImageResponse;
import com.devtechnology.api.domain.RxImageResult;
import com.google.gson.Gson;

/**
 * Utility methods for getting data from RxImage API
 * @author jbnimble
 */
public class RxImageUtil {
	private static Logger logger = Logger.getLogger(RxImageUtil.class);
	private String baseUrl = "http://rximage.nlm.nih.gov/api/rximage/1/rxnav?";
	
	/**
	 * NdcImage object with the image URL results matching the given 'ndc' value
	 * @param ndc
	 * @return
	 */
	public NdcImage getNdcUrl(String ndc) {
		NdcImage ndcImage = new NdcImage();
		ndcImage.setNdc(ndc);
		List<String> urls = new ArrayList<String>();
		String criteria = "ndc="+ndc;
		HttpOperations ops = new HttpOperations();
		String url = baseUrl+criteria;
		RxImageResponse rxImageResponse = ops.getMappedFromUlr(url, RxImageResponse.class);
		if (rxImageResponse == null) {
			logger.warn("Failed to load data using url="+baseUrl+criteria);
		}
		logger.info(new Gson().toJson(rxImageResponse));
		if (rxImageResponse != null && !rxImageResponse.getNlmRxImages().isEmpty()) {
			for (RxImageResult image : rxImageResponse.getNlmRxImages()) {
				if (image != null && image.getImageUrl() != null) {
					urls.add(image.getImageUrl());
				}
			}
		}
		ndcImage.getUrl().addAll(urls);
		return ndcImage;
	}
}
