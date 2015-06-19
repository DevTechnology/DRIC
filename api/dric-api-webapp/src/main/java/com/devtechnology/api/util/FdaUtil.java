package com.devtechnology.api.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.devtechnology.api.domain.FdaResponse;
import com.devtechnology.api.domain.FdaResults;
import com.devtechnology.api.domain.RecallItem;
import com.devtechnology.api.domain.RecallResponse;
import com.google.gson.Gson;

public class FdaUtil {
	private static Logger logger = Logger.getLogger(FdaUtil.class);
	private String baseUrl = "https://api.fda.gov/drug/enforcement.json?";
	// TODO get the openFdaApiKey and add to URL
	public RecallResponse getRecentRecalls() {
		String criteria = "search=report_date:[20140601+TO+20150618]&limit=10";
		HttpOperations ops = new HttpOperations();
		String httpResultStr = ops.getFromUrl(baseUrl+criteria);
		FdaResponse fdaResponse = new Gson().fromJson(httpResultStr, FdaResponse.class);
		logger.info(new Gson().toJson(fdaResponse));
		RecallResponse result = mapResponse(fdaResponse);
		return result;
	}
	
	private RecallResponse mapResponse(FdaResponse fdaResponse) {
		RecallResponse result = new RecallResponse();
		if (fdaResponse != null) {
			result.setMeta(fdaResponse.getMeta());
			result.setError(fdaResponse.getError());
			List<RecallItem> list = new ArrayList<>();
			for (FdaResults fdaResult : fdaResponse.getResults()) {
				Set<String> productSet = new HashSet<String>();
				Set<String> ndcSet = new HashSet<String>();
				RecallItem item = new RecallItem();
				if (fdaResult != null) {
					// fill basic information
					item.setRecall_number(fdaResult.getRecall_number());
					item.setReason_for_recall(fdaResult.getReason_for_recall());
					item.setStatus(fdaResult.getStatus());
					item.setDistribution_pattern(fdaResult.getDistribution_pattern());
					item.setProduct_quantity(fdaResult.getProduct_quantity());
					item.setRecall_initiation_date(fdaResult.getRecall_initiation_date());
					item.setState(fdaResult.getState());
					item.setEvent_id(fdaResult.getEvent_id());
					item.setProduct_type(fdaResult.getProduct_type());
					item.setProduct_description(fdaResult.getProduct_description());
					item.setCountry(fdaResult.getCountry());
					item.setCity(fdaResult.getCity());
					item.setRecalling_firm(fdaResult.getRecalling_firm());
					item.setReport_date(fdaResult.getReport_date());
					item.setVoluntary_mandated(fdaResult.getVoluntary_mandated());
					item.setClassification(fdaResult.getClassification());
					item.setCode_info(fdaResult.getCode_info());
					item.setInitial_firm_notification(fdaResult.getInitial_firm_notification());
					if (fdaResult.getOpenfda() != null) {
						productSet.addAll(fdaResult.getOpenfda().getGeneric_name());
						productSet.addAll(fdaResult.getOpenfda().getBrand_name());
						productSet.addAll(fdaResult.getOpenfda().getSubstance_name());
						ndcSet.addAll(fdaResult.getOpenfda().getProduct_ndc());
					}
					// loop through all ndc values, and attempt to get a URL to an image
//					for (String ndc : ndcSet) {
//						if (ndc != null) {
//							item.getImages().add(getNdcUrl(ndc));
//						}
//					}
				}
				item.getProduct().addAll(productSet);
				list.add(item);
			}
			result.getRecalls().addAll(list);
		}
		return result;
	}
	
//	private NdcImage getNdcUrl(String ndc) {
//		NdcImage ndcImage = new NdcImage();
//		ndcImage.setNdc(ndc);
//		// call service, urlEncode the URL value
//		RxImageUtil rxImageUtil = new RxImageUtil();
//		ndcImage.setUrl(rxImageUtil.getNdcUrl(ndc));
//		return ndcImage;
//	}

}