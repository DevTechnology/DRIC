package com.devtechnology.api.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.devtechnology.api.domain.RxImageResponse;
import com.devtechnology.api.domain.RxImageResult;
import com.google.gson.Gson;


public class RxImageUtil {
	private static Logger logger = Logger.getLogger(RxImageUtil.class);
	private String baseUrl = "http://rximage.nlm.nih.gov/api/rximage/1/rxnav?";
	public List<String> getNdcUrl(String ndc) {
		List<String> result = new ArrayList<String>();
		String criteria = "ndc="+ndc;
		HttpOperations ops = new HttpOperations();
		String httpResultStr = ops.getFromUrl(baseUrl+criteria);
		RxImageResponse rxImageResponse = new Gson().fromJson(httpResultStr, RxImageResponse.class);
		logger.info(new Gson().toJson(rxImageResponse));
		if (rxImageResponse != null && !rxImageResponse.getNlmRxImages().isEmpty()) {
			for (RxImageResult image : rxImageResponse.getNlmRxImages()) {
				if (image != null && image.getImageUrl() != null) {
					result.add(image.getImageUrl());
				}
			}
		}
		return result;
	}
}
