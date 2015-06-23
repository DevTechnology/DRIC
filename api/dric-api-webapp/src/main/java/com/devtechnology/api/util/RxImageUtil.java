package com.devtechnology.api.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.devtechnology.api.domain.NdcImage;
import com.devtechnology.api.domain.RxImageResponse;
import com.devtechnology.api.domain.RxImageResult;
import com.google.gson.Gson;

/**
 * Utility methods for getting data from RxImage API
 * Reference: http://rximage.nlm.nih.gov/docs/doku.php
 * Reference: http://rximage.nlm.nih.gov/docs/doku.php?id=imageretrieval:examples:request_by_ndc
 * @author jbnimble
 */
public class RxImageUtil {
	private static Logger logger = Logger.getLogger(RxImageUtil.class);
	private String baseUrl = "http://rximage.nlm.nih.gov/api/rximage/1/rxnav?";
	private HttpOps httpOps;
	
	/**
	 * NdcImage object with the image URL results matching the given 'ndc' value
	 * @param ndc
	 * @return
	 */
	public NdcImage getNdcUrl(String ndc) {
		String criteria = "ndc="+ndc;
		String url = baseUrl+criteria;
		RxImageResponse rxImageResponse = httpOps.getMappedFromUlr(url, RxImageResponse.class);
		if (rxImageResponse == null) {
			logger.warn("Failed to load data using url="+url);
		}
		NdcImage ndcImage = mapResponse(ndc, rxImageResponse);
		return ndcImage;
	}
	
	/**
	 * Get the first image URLs from a comma separated list of NDC values
	 * @param ndcs
	 * @return
	 */
	public NdcImage getNdcsImageUrl(String ndcs) {
		NdcImage ndcImage = null;
		if (ndcs != null && !ndcs.trim().isEmpty() && !ndcs.equals("undefined")) {
			String[] ndcArr = ndcs.split(",");
			for (String ndc : ndcArr) {
				if (ndc != null) {
					ndcImage = getNdcUrl(ndc);
					if (ndcImage != null && !ndcImage.getUrl().isEmpty()) {
						break;
					}
				}
			}
		}
		if (ndcImage == null) {
			ndcImage = new NdcImage();
		}
		return ndcImage;
	}
	
	/**
	 * Map the external service results to an NdcImage
	 * @param ndc
	 * @param rxImageResponse
	 * @return
	 */
	public NdcImage mapResponse(String ndc, RxImageResponse rxImageResponse) {
		NdcImage ndcImage = new NdcImage();
		ndcImage.setNdc(ndc);
		logger.info(new Gson().toJson(rxImageResponse));
		if (rxImageResponse != null && !rxImageResponse.getNlmRxImages().isEmpty()) {
			// get unique URL values and set to NdcImage
			Set<String> urls = new HashSet<String>();
			for (RxImageResult image : rxImageResponse.getNlmRxImages()) {
				if (image != null && image.getImageUrl() != null) {
					urls.add(image.getImageUrl());
				}
			}
			ndcImage.getUrl().addAll(urls);
		}
		return ndcImage;
	}
	
	/**
	 * Used to inject an instance of HttpOps for ease of unit testing
	 * @param httpOps
	 */
	public void setHttpOperations(HttpOps HttpOps) {
		this.httpOps = HttpOps;
	}
}
